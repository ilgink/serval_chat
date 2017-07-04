package org.servalproject.servalchat.feeds;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;

import org.servalproject.mid.Identity;
import org.servalproject.mid.Messaging;
import org.servalproject.mid.Peer;
import org.servalproject.servalchat.R;
import org.servalproject.servalchat.navigation.IHaveMenu;
import org.servalproject.servalchat.navigation.ILifecycle;
import org.servalproject.servalchat.navigation.INavigate;
import org.servalproject.servalchat.navigation.MainActivity;
import org.servalproject.servalchat.navigation.Navigation;
import org.servalproject.servalchat.views.RecyclerHelper;
import org.servalproject.servaldna.meshmb.MeshMBCommon;

/**
 * Created by jeremy on 3/08/16.
 */
public class PeerFeed extends LinearLayout
		implements INavigate, IHaveMenu, MenuItem.OnMenuItemClickListener {

	RecyclerView list;
	PeerFeedPresenter presenter;
	MainActivity activity;

	public PeerFeed(Context context, @Nullable AttributeSet attrs) {
		super(context, attrs);
	}

	private static final int FOLLOW = 1;
	private static final int IGNORE = 2;
	private static final int BLOCK = 3;

	@Override
	public void populateItems(Menu menu) {
		Messaging.SubscriptionState state = presenter.getSubscriptionState();
		if (state == null)
			return;
		if (state != Messaging.SubscriptionState.Ignored)
			menu.add(Menu.NONE, IGNORE, Menu.NONE, R.string.ignore_feed)
					.setOnMenuItemClickListener(this);
		if (state != Messaging.SubscriptionState.Followed)
			menu.add(Menu.NONE, FOLLOW, Menu.NONE, R.string.follow_feed)
					.setOnMenuItemClickListener(this);
		if (state != Messaging.SubscriptionState.Blocked)
			menu.add(Menu.NONE, BLOCK, Menu.NONE, R.string.block_contact)
					.setOnMenuItemClickListener(this);
	}

	@Override
	public boolean onMenuItemClick(MenuItem item) {
		switch (item.getItemId()) {
			case FOLLOW:
				presenter.subscribe(MeshMBCommon.SubscriptionAction.Follow);
				break;
			case IGNORE:
				presenter.subscribe(MeshMBCommon.SubscriptionAction.Ignore);
				break;
			case BLOCK:
				presenter.subscribe(MeshMBCommon.SubscriptionAction.Block);
				break;
			default:
				return false;
		}
		return true;
	}

	@Override
	public ILifecycle onAttach(MainActivity activity, Navigation n, Identity id, Peer peer, Bundle args) {
		this.activity = activity;
		this.list = (RecyclerView) findViewById(R.id.list);
		RecyclerHelper.createLayoutManager(list, true, false);
		return presenter = PeerFeedPresenter.factory.getPresenter(this, id, peer, args);
	}

}
