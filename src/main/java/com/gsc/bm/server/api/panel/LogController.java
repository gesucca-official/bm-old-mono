package com.gsc.bm.server.api.panel;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gsc.bm.server.repo.external.GameLogRecord;
import com.gsc.bm.server.repo.external.GameLogRepository;
import com.gsc.bm.server.service.session.GameLoggingService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/control-panel/logs")
public class LogController {

    private final GameLogRepository gameLogRepo;

    @Autowired
    public LogController(GameLogRepository gameLogRepo) {
        this.gameLogRepo = gameLogRepo;
    }

    @ApiOperation("Tell how many Games has been logged")
    @GetMapping(value = "/games/qty", produces = MediaType.APPLICATION_JSON_VALUE)
    public long getLoggedGamesQty() {
        return gameLogRepo.count();
    }

    @ApiOperation("Find all abandoned Games")
    @GetMapping(value = "/games/open", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<GameLogRecord> getOpenedGames() {
        List<GameLogRecord> openGames = new ArrayList<>();
        openGames.addAll(gameLogRepo.findAllByStatus(GameLoggingService.STARTED));
        openGames.addAll(gameLogRepo.findAllByStatus(GameLoggingService.IN_PROGRESS));
        return openGames;
    }

    @ApiOperation("Drain all Game Logs from DB")
    @GetMapping(value = "/games/drain", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public byte[] drainLogs() throws JsonProcessingException {
        List<GameLogRecord> logs = new ArrayList<>((int) gameLogRepo.count());
        Iterable<GameLogRecord> records = gameLogRepo.findAll();
        records.forEach(logs::add);
        gameLogRepo.deleteAll(records);
        return new ObjectMapper().writeValueAsBytes(logs);
    }

}
