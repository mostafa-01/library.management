package maids.library.management.resource.boundry;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import maids.library.management.repos.patron.entity.Patron;
import maids.library.management.resource.control.UserController;
import maids.library.management.resource.model.PatronAddDTO;
import maids.library.management.resource.model.PatronSummaryModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Service
@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserResource {
    private final UserController patronCtrl;

    @GetMapping
    public List<PatronSummaryModel> getAllPatrons() {
        return patronCtrl.getAllPatrons();
    }

    @GetMapping("/{id}")
    public ResponseEntity<PatronSummaryModel> getPatronById(@PathVariable Long id) {
        PatronSummaryModel patron = patronCtrl.getPatronSummaryById(id);
        return ResponseEntity.ok(patron);
    }

    @PostMapping
    public ResponseEntity<PatronSummaryModel> createPatron(@Valid @RequestBody PatronAddDTO patronDTO) {

        return new ResponseEntity<>(patronCtrl.insertNewPatron(patronDTO), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PatronSummaryModel> updatePatron(@PathVariable Long id, @Valid @RequestBody PatronAddDTO patronDTO) {
        Optional<Patron> existingPatron = patronCtrl.getPatronById(id);
        if (existingPatron.isPresent()) {
            Patron patron = existingPatron.get();

            return ResponseEntity.ok(patronCtrl.updatePatron(patronDTO));
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePatron(@PathVariable Long id) {
        patronCtrl.deletePatron(id);
        return ResponseEntity.noContent().build();
    }

}
