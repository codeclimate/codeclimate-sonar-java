package cc.models;

class Location {
    final String path;
    final Lines lines;

    public Location(String path, Integer startLine, Integer endLine) {
        this.path = path.replaceFirst("^/tmp/code/", "");
        this.lines = new Lines(startLine, endLine);
    }
}
