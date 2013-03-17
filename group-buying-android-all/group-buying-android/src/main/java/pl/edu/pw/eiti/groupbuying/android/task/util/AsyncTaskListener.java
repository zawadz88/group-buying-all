package pl.edu.pw.eiti.groupbuying.android.task.util;

import pl.edu.pw.eiti.groupbuying.android.task.AbstractGroupBuyingTask;

public interface AsyncTaskListener {

	void onTaskFinished(AbstractGroupBuyingTask<?> task, TaskResult result);
	
}
