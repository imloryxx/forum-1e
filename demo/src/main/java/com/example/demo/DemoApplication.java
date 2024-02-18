import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.*;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class ForumApplication {

    public static void main(String[] args) {
        SpringApplication.run(ForumApplication.class, args);
    }

}

@RestController
@RequestMapping("/api")
class ProblemController {

    private final List<Problem> problems = new ArrayList<>();

    @PostMapping("/submit_problem")
    public String submitProblem(@RequestBody Problem problem) {
        problems.add(problem);
        writeProblemToCSV(problem);
        return "Problema inviato con successo!";
    }

    private void writeProblemToCSV(Problem problem) {
        String csvFile = "forum.csv";

        try (FileWriter writer = new FileWriter(csvFile, true)) {
            writer.append(problem.getCategory());
            writer.append(",");
            writer.append(problem.getProblem());
            writer.append("\n");

            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @GetMapping("/problems")
    public List<Problem> getProblems() {
        return problems;
    }

}

class Problem {
    private String category;
    private String problem;

    // Getters and setters...
}
