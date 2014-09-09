/**
 * 
 */
package org.javahispano.javaleague.client.mvp.ui;

import org.gwtbootstrap3.client.ui.DescriptionData;
import org.gwtbootstrap3.client.ui.NavbarLink;
import org.javahispano.javaleague.client.ClientFactory;
import org.javahispano.javaleague.client.service.RPCCall;
import org.javahispano.javaleague.shared.domain.FrameWork;

import com.google.gwt.core.client.GWT;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.i18n.client.DateTimeFormat.PredefinedFormat;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

/**
 * @author adou
 *
 */
public class AppFrameWork extends Composite {

	private static AppFrameWorkUiBinder uiBinder = GWT
			.create(AppFrameWorkUiBinder.class);
	private ClientFactory clientFactory = GWT.create(ClientFactory.class);

	interface AppFrameWorkUiBinder extends UiBinder<Widget, AppFrameWork> {
	}

	@UiField
	DescriptionData nameFrameWork;
	@UiField
	DescriptionData versionFrameWork;
	@UiField
	DescriptionData createdFrameWork;
	@UiField
	DescriptionData updatedFrameWork;
	@UiField
	NavbarLink downloadFrameWorkNavbarLink;

	public AppFrameWork() {
		initWidget(uiBinder.createAndBindUi(this));

		setUp();
	}

	private void setUp() {
		getDefaultFrameWork();

	}

	private void getDefaultFrameWork() {
		new RPCCall<FrameWork>() {

			@Override
			public void onFailure(Throwable caught) {
				GWT.log(caught.getMessage());
				Window.alert("Error fetch frameWork ...");
			}

			@Override
			public void onSuccess(FrameWork result) {
				nameFrameWork.setHTML(result.getName());
				versionFrameWork.setHTML(result.getVersion());
				createdFrameWork.setHTML(DateTimeFormat.getFormat(
						PredefinedFormat.DATE_TIME_MEDIUM).format(
						result.getCreation()));
				createdFrameWork.setHTML(DateTimeFormat.getFormat(
						PredefinedFormat.DATE_TIME_MEDIUM).format(
						result.getUpdated()));
				downloadFrameWorkNavbarLink.setHref(result.getUrlDownload());
			}

			@Override
			protected void callService(AsyncCallback<FrameWork> cb) {
				clientFactory.getFrameWorkService().getDefaultFrameWork(cb);
			}

		}.retry(3);
	}

}
