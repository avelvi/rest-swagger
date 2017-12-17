package com.avelvi;

import com.avelvi.ws.api.TestsApi;
import com.avelvi.ws.model.TestCreateDTO;
import com.avelvi.ws.model.TestDTO;
import com.avelvi.ws.model.TestUpdateDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

import static java.util.Objects.nonNull;

@RestController
public class TestController implements TestsApi {

    private static Map<Long, Test> testData = new HashMap<>();
    private static AtomicLong counter = new AtomicLong();

    @Override
    public ResponseEntity<List<TestDTO>> getAll() {
        return ResponseEntity.ok(
                testData.values()
                        .stream()
                        .map(this::convertToDTO)
                        .collect(Collectors.toList())
        );
    }

    @Override
    public ResponseEntity<TestDTO> get(@PathVariable("testId") Long testId) {
        Test test = testData.get(testId);
        if (nonNull(test)) {
            return ResponseEntity.ok(this.convertToDTO(test));
        }
        throw new ResourceNotFoundException(String.format("Resource with id = %s not found", testId));
    }

    @Override
    public ResponseEntity<TestDTO> create(@Valid @RequestBody TestCreateDTO createTest) {
        long id = getNextUniqueIndex();
        Test test = new Test(id, createTest.getName(), LocalDate.now(), LocalDateTime.now(), id % 2 == 0 ? false : true);
        testData.put(id, test);
        return ResponseEntity.ok(convertToDTO(test));
    }

    @Override
    public ResponseEntity<TestDTO> update(@PathVariable("testId") Long testId,
                                          @Valid @RequestBody TestUpdateDTO testUpdate) {
        Test test = testData.get(testId);
        if (nonNull(test)) {
            test.setName(testUpdate.getName());
            return ResponseEntity.ok(convertToDTO(testData.put(testId, test)));
        }
        throw new ResourceNotFoundException(String.format("Resource with id = %s not found", testId));
    }

    @Override
    public ResponseEntity<Void> delete(@PathVariable("testId") Long testId) {
        Test test = testData.get(testId);
        if (nonNull(test)) {
            testData.remove(testId);
            return ResponseEntity.ok(null);
        }
        throw new ResourceNotFoundException(String.format("Resource with id = %s not found", testId));
    }

    private TestDTO convertToDTO(Test test) {
        TestDTO testDTO = new TestDTO()
                .testId(test.getId())
                .name(test.getName())
                .currentDate(test.getCurrentDate())
                .currentDateTime(test.getCurrentDateTime().atOffset(ZoneOffset.UTC))
                .isIdOdd(test.getIdOdd());

        return testDTO;
    }

    private long getNextUniqueIndex() {
        return counter.getAndIncrement();
    }
}
