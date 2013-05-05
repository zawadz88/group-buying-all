package pl.edu.pw.eiti.groupbuying.android.task.util;

import pl.edu.pw.eiti.groupbuying.android.task.AbstractGroupBuyingTask;

public interface AsyncTaskListener {

	void onTaskFinished(final AbstractGroupBuyingTask<?> task, final TaskResult result);
	
}
