package maids.library.management.resource.control;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import maids.library.management.exceptions.InsertionFailedException;
import maids.library.management.exceptions.NotFoundException;
import maids.library.management.repos.patron.entity.Patron;
import maids.library.management.repos.patron.repo.PatronRepo;
import maids.library.management.resource.model.PatronAddDTO;
import maids.library.management.resource.model.PatronSummaryModel;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserController {
    private final ObjectMapper objectMapper;
    private final PatronRepo repo;

    public List<PatronSummaryModel> getAllPatrons() {
        List<Patron> patrons = repo.findAll();
        if(patrons.isEmpty())
            throw new NotFoundException("No patrons found! add sum... :)");
        return patrons.stream()
                .map(this::convertToSummaryModel)
                .toList();
    }

    public PatronSummaryModel getPatronSummaryById(Long id) {
        Optional<Patron> patron = repo.findById(id);
        if(patron.isPresent())
            return convertToSummaryModel(patron.get());
        else
            throw new NotFoundException("patron with Id:"+id+" couldn't be found!");
    }

    public Optional<Patron> getPatronById(Long id){
        Optional<Patron> patron = repo.findById(id);
        if(patron.isPresent())
            return patron;
        else
            throw new NotFoundException("patron with Id:"+id+" couldn't be found!");
    }



    public PatronSummaryModel insertNewPatron(PatronAddDTO patronDTO) {
        try{
            repo.save(convertToEntity(patronDTO));
            PatronSummaryModel patron;
            patron = patronDTO;
            return patron;
        }catch (Exception ex){
            throw new InsertionFailedException();
        }
    }

    public PatronSummaryModel updatePatron(PatronAddDTO patron) {
        try{
            repo.save(convertToEntity(patron));
            return patron;
        }catch (Exception ex){
            throw new InsertionFailedException();
        }
    }

    public void deletePatron(Long id) {
        repo.deleteById(id);
    }

    private PatronSummaryModel convertToSummaryModel(Patron patron) {
        return objectMapper.convertValue(patron, PatronSummaryModel.class);
    }

    private Patron convertToEntity (PatronAddDTO patron) {
        return objectMapper.convertValue(patron, Patron.class);
    }
}
