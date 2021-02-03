import {UI_CardInHand} from "../model/ui-card-in-hand";
import {HighlightAnimation} from "./highlight";
import {DetailsAnimation} from "./details";

export class DiscardChoiceAnimation {

  private static _INSTANCE: DiscardChoiceAnimation;

  public static getInstance(): DiscardChoiceAnimation {
    if (!this._INSTANCE)
      this._INSTANCE = new DiscardChoiceAnimation();
    return this._INSTANCE;
  }

  public triggerDiscardChoice(scene: Phaser.Scene, playedCard: UI_CardInHand, callback: (cardToDiscard: string) => void) {
    HighlightAnimation.getInstance().resetHighlight(playedCard, scene)
    // I have to let the reset highlight animation end before zooming
    // TODO find a way to intercept the callback of the reset hightlight tween
    setTimeout(() => {
      DetailsAnimation.getInstance().toggleDetails(playedCard.getId())
      DetailsAnimation.getInstance().focusDetails(playedCard, scene)
      DetailsAnimation.getInstance().zoomObjForDetails(playedCard, scene)
      playedCard.getContainer().removeAllListeners();
      playedCard.getContainer().setDepth(playedCard.getContainer().depth + 10);
      playedCard.toggleDetailsButton();
      // todo choice by drop zone and fire up callback?
    }, 300);

  }
}
