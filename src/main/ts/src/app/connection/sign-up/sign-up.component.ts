import {Component} from '@angular/core';
import {ErrorStateMatcher} from "@angular/material/core";
import {FormBuilder, FormControl, FormGroup, FormGroupDirective, NgForm, Validators} from "@angular/forms";
import {HttpClient} from "@angular/common/http";

export class MyErrorStateMatcher implements ErrorStateMatcher {
  isErrorState(control: FormControl | null, form: FormGroupDirective | NgForm | null): boolean {
    if (control.parent === undefined) {
      const isSubmitted = form && form.submitted;
      return !!(control && control.invalid && (control.dirty || control.touched || isSubmitted));
    } else {
      const invalidCtrl = !!(control && control.invalid && control.parent.dirty);
      const invalidParent = !!(control && control.parent && control.parent.invalid && control.parent.dirty);
      return (invalidCtrl || invalidParent);
    }
  }
}

@Component({
  selector: 'app-register',
  templateUrl: './sign-up.component.html',
  styleUrls: ['./sign-up.component.css']
})
export class SignUpComponent {

  sendCodeClicked = false;
  registerClicked = false;

  playerIdFormControl = new FormControl('', [
    Validators.required,
    Validators.pattern('^[A-Za-z0-9]+$')
  ]);

  emailFormControl = new FormControl('', [
    Validators.required,
    Validators.email,
  ]);

  verificationCodeFormControl = new FormControl('', [
    Validators.required,
    Validators.minLength(6),
    Validators.maxLength(6),
    Validators.pattern('^[0-9]+$')
  ]);

  passwordForm: FormGroup;

  matcher = new MyErrorStateMatcher();

  constructor(protected http: HttpClient, private formBuilder: FormBuilder) {
    this.passwordForm = this.formBuilder.group({
      password: ['', [Validators.required]],
      confirmPassword: ['']
    }, { validator: this.checkPasswords });
  }

  sendVerificationEmail() {
    this.sendCodeClicked = true;
    setTimeout(() => this.sendCodeClicked = false, 10000)
    this.http.get<boolean>('/sign-up/users/' + this.playerIdFormControl.value + '/available').subscribe(res => {
      console.log(res)
      if (!res)
        alert('username already taken');
      else this.http.get<boolean>('/sign-up/mail/' + this.emailFormControl.value + '/available').subscribe(res => {
        console.log(res)
        if (!res)
          alert('email already taken');
        else
          this.http.post<void>('/sign-up/verify', {
            username: this.playerIdFormControl.value,
            email: this.emailFormControl.value
          }).subscribe(res => {
            console.log(res)
            this.sendCodeClicked = false;
            alert('email sent')
          })
      });
    });
  }

  signUp() {
    this.registerClicked = true;
    this.http.post<void>('/sign-up/register', {
      username: this.playerIdFormControl.value,
      password: this.passwordForm.value.password,
      email: this.emailFormControl.value,
      code: this.verificationCodeFormControl.value
    }).subscribe(res => {
      console.log(res)
      alert('account created')
    })
  }

  checkPasswords(group: FormGroup) {
    let pass = group.controls.password.value;
    let confirmPass = group.controls.confirmPassword.value;
    return pass === confirmPass ? null : { notSame: true }
  }
}
