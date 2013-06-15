package pl.edu.pw.eiti.groupbuying.partner.android.task.util;

import pl.edu.pw.eiti.groupbuying.partner.android.task.AbstractGroupBuyingTask;

public interface AsyncTaskListener {

	void onTaskFinished(final AbstractGroupBuyingTask<?> task, final TaskResult result);
	
}
