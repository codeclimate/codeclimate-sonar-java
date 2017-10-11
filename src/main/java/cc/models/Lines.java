package cc.models;

class Lines {
    final Integer begin;
    final Integer end;

    public Lines(Integer startLine, Integer endLine) {
        if (startLine != null) {
            this.begin = startLine;

            if (endLine != null) {
                this.end = endLine;
            } else {
                this.end = 1;
            }
        } else {
            this.begin = 1;
            this.end = 1;
        }

    }
}
