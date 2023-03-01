package org.micro.los.common.protocol;

import org.slf4j.event.Level;

public class SampleLog extends BaseLog {

    private static final long serialVersionUID = 1L;

    public SampleLog() {
        super();
    }

    public SampleLog(String title, String content) {
        super(title, content);
    }

    public SampleLog(String buzz, String title, String content) {
        super(buzz, title, content);
    }

    public SampleLog(Level level, String title, String content) {
        super(level, title, content);
    }

    public SampleLog(Level level, String buzz, String title, String content) {
        super(level, buzz, title, content);
    }

}
