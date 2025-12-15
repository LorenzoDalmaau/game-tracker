package com.ceac.gametracker.match;

import com.ceac.gametracker.game.Game;
import com.ceac.gametracker.game.GameRepository;
import com.ceac.gametracker.match.dto.MatchCreateRequest;
import com.ceac.gametracker.match.dto.MatchResponse;
import com.ceac.gametracker.match.dto.MatchUpdateRequest;
import com.ceac.gametracker.user.User;
import com.ceac.gametracker.user.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MatchService {

    private final MatchRepository matchRepository;
    private final UserRepository userRepository;
    private final GameRepository gameRepository;

    public MatchService(MatchRepository matchRepository, UserRepository userRepository, GameRepository gameRepository) {
        this.matchRepository = matchRepository;
        this.userRepository = userRepository;
        this.gameRepository = gameRepository;
    }

    public List<MatchResponse> myMatches(String username) {
        return matchRepository.findByUser_Username(username).stream()
                .map(this::toResponse)
                .toList();
    }

    public MatchResponse create(String username, MatchCreateRequest req) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("Usuario no existe"));

        Game game = gameRepository.findById(req.gameId())
                .orElseThrow(() -> new IllegalArgumentException("El juego no existe"));

        Match match = new Match(user, game, req.result(), req.score());

        return toResponse(matchRepository.save(match));
    }

    public MatchResponse update(String username, Long matchId, MatchUpdateRequest req) {
        Match match = matchRepository.findById(matchId)
                .orElseThrow(() -> new IllegalArgumentException("Match no existe"));

        // Seguridad lógica: solo el dueño puede modificar
        if (!match.getUser().getUsername().equals(username)) {
            throw new IllegalArgumentException("No puedes modificar partidas de otro usuario");
        }

        match.update(req.result(), req.score());

        return toResponse(matchRepository.save(match));
    }

    public void delete(String username, Long matchId) {
        Match match = matchRepository.findById(matchId)
                .orElseThrow(() -> new IllegalArgumentException("Match no existe"));

        if (!match.getUser().getUsername().equals(username)) {
            throw new IllegalArgumentException("No puedes borrar partidas de otro usuario.");
        }

        matchRepository.delete(match);
    }


    private MatchResponse toResponse(Match match) {
        return new MatchResponse(
                match.getId(),
                match.getGame().getId(),
                match.getGame().getName(),
                match.getResult(),
                match.getScore(),
                match.getPlayedAt()
        );
    }
}
