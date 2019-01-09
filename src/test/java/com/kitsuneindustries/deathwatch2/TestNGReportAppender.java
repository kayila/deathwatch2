package com.kitsuneindustries.deathwatch2;

import java.io.Serializable;

import org.apache.logging.log4j.core.Filter;
import org.apache.logging.log4j.core.Layout;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.appender.AbstractAppender;
import org.testng.Reporter;

public class TestNGReportAppender extends AbstractAppender {

	public TestNGReportAppender(String name, Filter filter, Layout<? extends Serializable> layout) {
		super(name, filter, layout, false);
	}
	
	@Override
	public void append(final LogEvent event) {
		Reporter.log(event.toString());
	}
}
