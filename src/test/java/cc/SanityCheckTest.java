package cc;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


public class SanityCheckTest {
    @Test
    public void executeJavaLibFixture() throws Exception {
        List<String> expectedOutput = Arrays.asList("{\"type\":\"issue\"," +
                        "\"check_name\":\"squid:S1994\"," +
                        "\"severity\":\"major\"," +
                        "\"description\":\"This loop's stop condition tests \\\"k\\\" but the incrementer updates \\\"i\\\".\"," +
                        "\"content\":{\"body\":\"<p>It is almost always an error when a <code>for</code> loop's stop condition and incrementer don't act on the same variable. Even when it is not," +
                        " it\\ncould confuse future maintainers of the code," +
                        " and should be avoided.</p>\\n<h2>Noncompliant Code Example</h2>\\n<pre>\\nfor (i = 0; i &lt; 10; j++) {  // Noncompliant\\n  // ...\\n}\\n</pre>\\n<h2>Compliant Solution</h2>\\n<pre>\\nfor (i = 0; i &lt; 10; i++) {\\n  // ...\\n}\\n</pre>\"}," +
                        "\"location\":{\"path\":\"fixtures/java_lib/main/java/Library.java\"," +
                        "\"lines\":{\"begin\":17," +
                        "\"end\":17}}," +
                        "\"categories\":[\"Bug Risk\"]}\u0000",
                "{\"type\":\"issue\"," +
                        "\"check_name\":\"squid:S1994\"," +
                        "\"severity\":\"major\"," +
                        "\"description\":\"This loop's stop condition tests \\\"k\\\" but the incrementer updates \\\"i\\\".\"," +
                        "\"content\":{\"body\":\"<p>It is almost always an error when a <code>for</code> loop's stop condition and incrementer don't act on the same variable. Even when it is not," +
                        " it\\ncould confuse future maintainers of the code," +
                        " and should be avoided.</p>\\n<h2>Noncompliant Code Example</h2>\\n<pre>\\nfor (i = 0; i &lt; 10; j++) {  // Noncompliant\\n  // ...\\n}\\n</pre>\\n<h2>Compliant Solution</h2>\\n<pre>\\nfor (i = 0; i &lt; 10; i++) {\\n  // ...\\n}\\n</pre>\"}," +
                        "\"location\":{\"path\":\"fixtures/java_lib/main/java/Library.java\"," +
                        "\"lines\":{\"begin\":20," +
                        "\"end\":20}}," +
                        "\"categories\":[\"Bug Risk\"]}\u0000",
                "{\"type\":\"issue\"," +
                        "\"check_name\":\"squid:S1994\"," +
                        "\"severity\":\"major\"," +
                        "\"description\":\"This loop's stop condition tests \\\"k\\\" but the incrementer updates \\\"i\\\".\"," +
                        "\"content\":{\"body\":\"<p>It is almost always an error when a <code>for</code> loop's stop condition and incrementer don't act on the same variable. Even when it is not," +
                        " it\\ncould confuse future maintainers of the code," +
                        " and should be avoided.</p>\\n<h2>Noncompliant Code Example</h2>\\n<pre>\\nfor (i = 0; i &lt; 10; j++) {  // Noncompliant\\n  // ...\\n}\\n</pre>\\n<h2>Compliant Solution</h2>\\n<pre>\\nfor (i = 0; i &lt; 10; i++) {\\n  // ...\\n}\\n</pre>\"}," +
                        "\"location\":{\"path\":\"fixtures/java_lib/main/java/Library.java\"," +
                        "\"lines\":{\"begin\":23," +
                        "\"end\":23}}," +
                        "\"categories\":[\"Bug Risk\"]}\u0000");

        Shell.Process process = Shell.execute("build/codeclimate-sonar fixtures/java_lib");

        assertThat(process.exitCode).isEqualTo(0);
        assertThat(process.stdout).contains(expectedOutput);
    }
}
