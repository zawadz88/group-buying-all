/*******************************************************************************
 * Copyright (c) 2013 Piotr Zawadzki.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Public License v3.0
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/gpl.html
 * 
 * Contributors:
 *     Piotr Zawadzki - initial API and implementation
 ******************************************************************************/
package pl.edu.pw.eiti.groupbuying.android.fragment;

import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListAdapter;
import android.widget.ListView;

public abstract class AbstractListFragment extends Fragment implements OnItemClickListener {

	protected ListView listView;
	protected View loadingView;
	protected View emptyView;

	public enum ListViewState {
		IN_PROGRESS, CONTENT, EMPTY
	}

	public ListView getListView() {
		return listView;
	}

	public void setListAdapter(ListAdapter adapter) {
		listView.setAdapter(adapter);
	}

	protected ListAdapter getListAdapter() {
		if (listView == null) {
			return null;
		}
		return listView.getAdapter();
	}

	protected void setListViewState(ListViewState newState) {
		switch (newState) {
		case IN_PROGRESS:
			listView.setVisibility(View.GONE);
			emptyView.setVisibility(View.GONE);
			loadingView.setVisibility(View.VISIBLE);
			break;
		case CONTENT:
			listView.setVisibility(View.VISIBLE);
			emptyView.setVisibility(View.GONE);
			loadingView.setVisibility(View.GONE);
			break;
		case EMPTY:
			listView.setVisibility(View.GONE);
			emptyView.setVisibility(View.VISIBLE);
			loadingView.setVisibility(View.GONE);
			break;
		}
	}

	@Override
	public void onResume() {
		super.onResume();
		if (getListAdapter() == null) {
			setListViewState(ListViewState.IN_PROGRESS);
		}
	}

	/** Used to refresh screen state (list, infos, etc). */
	public abstract void refreshList();

}
