package com.ceac.gametracker.match;

import com.ceac.gametracker.match.dto.MatchCreateRequest;
import com.ceac.gametracker.match.dto.MatchResponse;
import com.ceac.gametracker.match.dto.MatchUpdateRequest;
import jakarta.validation.Valid;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/matches")
public class MatchController {

    private final MatchService service;

    public MatchController(MatchService service) {
        this.service = service;
    }

    @GetMapping
    public List<MatchResponse> myMatches(Authentication auth) {
        return service.myMatches(auth.getName()); // auth.getName() = username (sub)
    }

    @PostMapping
    public MatchResponse create(Authentication auth, @Valid @RequestBody MatchCreateRequest req) {
        return service.create(auth.getName(), req);
    }

    @PutMapping("/{id}")
    public MatchResponse update(Authentication auth, @PathVariable Long id, @Valid @RequestBody MatchUpdateRequest req) {
        return service.update(auth.getName(), id, req);
    }

    @DeleteMapping("/{id}")
    public void delete(Authentication auth, @PathVariable Long id) {
        service.delete(auth.getName(), id);
    }
}
