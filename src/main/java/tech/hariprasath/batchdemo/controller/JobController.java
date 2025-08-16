package tech.hariprasath.batchdemo.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/job")
@RequiredArgsConstructor
public class JobController {

    private final JobLauncher jobLauncher;
    private final Job job;

    @PostMapping("/launch-job")
    public Map<String, Object> launchJob() {
        Map<String, Object> map = new HashMap<>();

        final JobParameters jobParameters = new JobParametersBuilder()
                .addLong("startAt", System.currentTimeMillis()).toJobParameters();

        try {
            final JobExecution jobExecution = jobLauncher.run(job, jobParameters);
            map.put("success", true);
            map.put("status", jobExecution.getStatus().toString());
        } catch (Exception e) {
            map.put("success", false);
            map.put("msg", e.getMessage());
        }

        return map;
    }
}
