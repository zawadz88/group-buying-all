package pl.edu.pw.eiti.groupbuying.android.task;

import static pl.edu.pw.eiti.groupbuying.android.util.Constants.TAG;
import pl.edu.pw.eiti.groupbuying.android.task.util.AsyncTaskListener;
import pl.edu.pw.eiti.groupbuying.android.task.util.TaskResult;
import android.os.AsyncTask;
import android.util.Log;

public abstract class AbstractGroupBuyingTask<Result> extends AsyncTask<Void, Void, Result> {

	protected AsyncTaskListener taskListener;
	protected TaskResult taskResult = TaskResult.SUCCESSFUL;
	protected boolean cancelled;
	protected Exception exception;
	protected boolean containsErrors;

	
	@Override
	protected final Result doInBackground(Void... params) {
		try	{
			doInBackgroundSafe();
		} catch (Exception e)	{
			Log.e(TAG, e.getLocalizedMessage() != null ? e.getLocalizedMessage() : "null", e);
			containsErrors = true;
			this.exception = e;
		}
		return null;
	}
	
	protected abstract void doInBackgroundSafe() throws Exception;
	
	@Override
	protected void onPostExecute(Result result) {
		if (cancelled) {
			taskListener.onTaskFinished(this, TaskResult.CANCELED);
			return;
		}

		if (taskListener != null) {
			if (!containsErrors) {
				taskListener.onTaskFinished(this, TaskResult.SUCCESSFUL);
			} else {
				taskListener.onTaskFinished(this, TaskResult.FAILED);
			}
		}
	};

	@Override
	protected void onCancelled() {
		if (taskListener != null) {
			taskListener.onTaskFinished(this, TaskResult.CANCELED);
		}
	}
	
	public void cancel() {
		cancelled = true;
		onCancelled();
	}

	public boolean isCancelledOverride() {
		return cancelled;
	}
	
	
	public AsyncTaskListener getListener() {
		return taskListener;
	}

	public void setListener(AsyncTaskListener listener) {
		this.taskListener = listener;
	}
	
	public Exception getException() {
		return exception;
	}
}
