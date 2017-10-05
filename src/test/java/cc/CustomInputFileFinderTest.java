package cc;

import org.junit.Test;
import org.sonarsource.sonarlint.core.client.api.common.analysis.ClientInputFile;

import java.nio.charset.Charset;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;


public class CustomInputFileFinderTest {

    @Test
    public void find_files_in_directory() throws Exception {
        CustomInputFileFinder finder = new CustomInputFileFinder(Arrays.asList("src/included/"), null, null, null, Charset.defaultCharset());

        List<ClientInputFile> files = finder.collect(Paths.get("fixtures/multiple_paths"));
        List<String> paths = files.stream().map(ClientInputFile::getPath).collect(Collectors.toList());

        assertThat(paths).containsOnly(
                "fixtures/multiple_paths/src/included/java/pkg1/HasIssue.java",
                "fixtures/multiple_paths/src/included/java/pkg1/HasNoIssue.java"
        );
    }

    @Test
    public void find_specified_files() throws Exception {
        CustomInputFileFinder finder = new CustomInputFileFinder(Arrays.asList("config.json", "Main.java"), null, null, null, Charset.defaultCharset());

        List<ClientInputFile> files = finder.collect(Paths.get("fixtures/multiple_paths"));
        List<String> paths = files.stream().map(ClientInputFile::getPath).collect(Collectors.toList());

        assertThat(paths).containsOnly(
                "fixtures/multiple_paths/Main.java",
                "fixtures/multiple_paths/config.json"
        );
    }

    @Test
    public void find_from_multiple_locations() throws Exception {
        CustomInputFileFinder finder = new CustomInputFileFinder(Arrays.asList("config.json", "src/included/java/"), null, null, null, Charset.defaultCharset());

        List<ClientInputFile> files = finder.collect(Paths.get("fixtures/multiple_paths"));
        List<String> paths = files.stream().map(ClientInputFile::getPath).collect(Collectors.toList());

        assertThat(paths).containsOnly(
                "fixtures/multiple_paths/config.json",
                "fixtures/multiple_paths/src/included/java/pkg1/HasIssue.java",
                "fixtures/multiple_paths/src/included/java/pkg1/HasNoIssue.java"
        );
    }

    @Test
    public void keep_exclude_pattern_behaviour_on_directories() throws Exception {
        CustomInputFileFinder finder = new CustomInputFileFinder(Arrays.asList("config.json", "src/included/java/"), null, null, "**/HasNoIssue.*", Charset.defaultCharset());

        List<ClientInputFile> files = finder.collect(Paths.get("fixtures/multiple_paths"));
        List<String> paths = files.stream().map(ClientInputFile::getPath).collect(Collectors.toList());

        assertThat(paths).containsOnly(
                "fixtures/multiple_paths/config.json",
                "fixtures/multiple_paths/src/included/java/pkg1/HasIssue.java"
        );
    }

    @Test
    public void keep_exclude_pattern_behaviour_on_files() throws Exception {
        CustomInputFileFinder finder = new CustomInputFileFinder(Arrays.asList("config.json", "src/included/java/"), null, null, "**/*.json", Charset.defaultCharset());

        List<ClientInputFile> files = finder.collect(Paths.get("fixtures/multiple_paths"));
        List<String> paths = files.stream().map(ClientInputFile::getPath).collect(Collectors.toList());

        assertThat(paths).containsOnly(
                "fixtures/multiple_paths/src/included/java/pkg1/HasIssue.java",
                "fixtures/multiple_paths/src/included/java/pkg1/HasNoIssue.java"
        );
    }

    @Test
    public void differentiate_src_and_test() throws Exception {
        CustomInputFileFinder finder = new CustomInputFileFinder(Arrays.asList("src/included/", "src/test/"), "src/**", "**/test/**", null, Charset.defaultCharset());

        List<ClientInputFile> files = finder.collect(Paths.get("fixtures/multiple_paths"));

        assertThat(files.stream().filter(f -> !f.isTest()).map(ClientInputFile::getPath).collect(Collectors.toList())).containsOnly(
                "fixtures/multiple_paths/src/included/java/pkg1/HasIssue.java",
                "fixtures/multiple_paths/src/included/java/pkg1/HasNoIssue.java"
        );
        assertThat(files.stream().filter(f -> f.isTest()).map(ClientInputFile::getPath).collect(Collectors.toList())).containsOnly(
                "fixtures/multiple_paths/src/test/java/Test.java"
        );
    }


}