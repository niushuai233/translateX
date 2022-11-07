package cc.niushuai.translatex.config;

import lombok.Data;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Objects;

/**
 * 打印git构建信息
 *
 * @author niushuai
 * @date 2022/11/7 9:16
 */
@Component
public class GitPrinter implements ApplicationRunner {

    @Resource
    private GitBuildProperties buildProperties;

    @Override
    public void run(ApplicationArguments args) {

        StringBuffer git = new StringBuffer();
        git.append("888888888888                                        88                                 8b        d8  \n" +
                "     88                                             88              ,d                  Y8,    ,8P   \n" +
                "     88                                             88              88                   `8b  d8'    \n" +
                "     88 8b,dPPYba, ,adPPYYba, 8b,dPPYba,  ,adPPYba, 88 ,adPPYYba, MM88MMM ,adPPYba,        Y88P      \n" +
                "     88 88P'   \"Y8 \"\"     `Y8 88P'   `\"8a I8[    \"\" 88 \"\"     `Y8   88   a8P_____88        d88b      \n" +
                "     88 88         ,adPPPPP88 88       88  `\"Y8ba,  88 ,adPPPPP88   88   8PP\"\"\"\"\"\"\"      ,8P  Y8,    \n" +
                "     88 88         88,    ,88 88       88 aa    ]8I 88 88,    ,88   88,  \"8b,   ,aa     d8'    `8b   \n" +
                "     88 88         `\"8bbdP\"Y8 88       88 `\"YbbdP\"' 88 `\"8bbdP\"Y8   \"Y888 `\"Ybbd8\"'    8P        Y8\n")
                .append("Application Started Successful. ");
        if (null == buildProperties || buildProperties.isNull()) {
            System.out.println(git.append(System.lineSeparator()).toString());
            return;
        }

        git
                // 构建分支
                // 版本号
                .append("Version: ").append(buildProperties.getBuild().getVersion()).append(System.lineSeparator())
                .append("Build branch: ").append(buildProperties.getBranch()).append(System.lineSeparator())
                // 构建主机信息
                .append("Build on ").append(buildProperties.getBuild().getHost())
                .append(" With user ").append(buildProperties.getBuild().getUser().getName())
                .append(" At ").append(buildProperties.getBuild().getTime())
                .append(System.lineSeparator())
                // 提交人以及提交信息
                .append("Last commit by ").append(buildProperties.getCommit().getUser().getName())
                .append(" At ").append(buildProperties.getCommit().getTime())
                .append(" With ").append(buildProperties.getCommit().getMessage().getFull());

        System.out.println(git.toString());
    }
}

@Data
@ConfigurationProperties(prefix = "git")
@PropertySource(value = "classpath:git.properties", encoding = "UTF-8", ignoreResourceNotFound = true)
@Component
class GitBuildProperties {

    private String branch;

    private Build build;

    private Commit commit;

    public boolean isNull() {
        return Objects.isNull(branch) || Objects.isNull(build) || Objects.isNull(commit);
    }

    @Data
    static class Build {

        private String host;
        private String time;
        private String version;

        private User user;
    }

    @Data
    static class Commit {
        private String time;
        private User user;
        private Message message;
    }

    @Data
    static class Message {
        private String full;
    }

    @Data
    static class User {
        private String name;
        private String email;
    }
}
