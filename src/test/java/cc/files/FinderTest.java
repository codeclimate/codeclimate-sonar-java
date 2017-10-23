package cc.files;

import org.junit.Test;
import org.sonarsource.sonarlint.core.client.api.common.analysis.ClientInputFile;

import java.nio.charset.Charset;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;


public class FinderTest {

    @Test
    public void find_files_in_directory() throws Exception {
        Finder finder = new Finder(Arrays.asList("src/included/"), null, Charset.defaultCharset());

        List<ClientInputFile> files = finder.collect(Paths.get("fixtures/multiple_paths"));
        List<String> paths = files.stream().map(ClientInputFile::getPath).collect(Collectors.toList());

        assertThat(paths).containsOnly(
                "fixtures/multiple_paths/src/included/java/pkg1/HasIssue.java",
                "fixtures/multiple_paths/src/included/java/pkg1/HasNoIssue.java"
        );
    }

    @Test
    public void find_specified_files() throws Exception {
        Finder finder = new Finder(Arrays.asList("config.json", "Main.java"), null, Charset.defaultCharset());

        List<ClientInputFile> files = finder.collect(Paths.get("fixtures/multiple_paths"));
        List<String> paths = files.stream().map(ClientInputFile::getPath).collect(Collectors.toList());

        assertThat(paths).containsOnly("fixtures/multiple_paths/Main.java");
    }

    @Test
    public void find_from_multiple_locations() throws Exception {
        Finder finder = new Finder(Arrays.asList("config.json", "src/included/java/"), null, Charset.defaultCharset());

        List<ClientInputFile> files = finder.collect(Paths.get("fixtures/multiple_paths"));
        List<String> paths = files.stream().map(ClientInputFile::getPath).collect(Collectors.toList());

        assertThat(paths).containsOnly(
                "fixtures/multiple_paths/src/included/java/pkg1/HasIssue.java",
                "fixtures/multiple_paths/src/included/java/pkg1/HasNoIssue.java"
        );
    }

    @Test
    public void does_not_load_unecessary_files() throws Exception {
        List<String> includedPaths = Arrays.asList("config.json", "main/", "image.gif");
        Finder finder = new Finder(includedPaths, null, Charset.defaultCharset());

        List<ClientInputFile> files = finder.collect(Paths.get("fixtures/extra_files"));
        List<String> paths = files.stream().map(ClientInputFile::getPath).collect(Collectors.toList());

        assertThat(paths).containsOnly("fixtures/extra_files/main/java/Library.java");
    }

    @Test
    public void differentiate_src_and_test() throws Exception {
        Finder finder = new Finder(Arrays.asList("src/included/", "src/test/"), "{**/test/**}", Charset.defaultCharset());

        List<ClientInputFile> files = finder.collect(Paths.get("fixtures/multiple_paths"));

        assertThat(files.stream().filter(f -> !f.isTest()).map(ClientInputFile::getPath).collect(Collectors.toList())).containsOnly(
                "fixtures/multiple_paths/src/included/java/pkg1/HasIssue.java",
                "fixtures/multiple_paths/src/included/java/pkg1/HasNoIssue.java"
        );
        assertThat(files.stream().filter(f -> f.isTest()).map(ClientInputFile::getPath).collect(Collectors.toList())).containsOnly(
                "fixtures/multiple_paths/src/test/java/Test.java"
        );
    }

    @Test
    public void accept_multiple_test_patterns() throws Exception {
        Finder finder = new Finder(Arrays.asList("src/"), "{**/test/**,**/test2/**}", Charset.defaultCharset());

        List<ClientInputFile> files = finder.collect(Paths.get("fixtures/multiple_paths"));

        assertThat(files.stream().filter(f -> f.isTest()).map(ClientInputFile::getPath).collect(Collectors.toList())).containsOnly(
                "fixtures/multiple_paths/src/test/java/Test.java",
                "fixtures/multiple_paths/src/test2/java/Test.java"
        );
    }
}