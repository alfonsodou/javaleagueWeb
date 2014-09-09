/**
 * 
 */
package org.javahispano.javaleague.client.mvp.ui;

import org.gwtbootstrap3.client.ui.html.Paragraph;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

/**
 * @author adou
 *
 */
public class AppShowMatch extends Composite {

	private static AppShowMatchUiBinder uiBinder = GWT
			.create(AppShowMatchUiBinder.class);

	interface AppShowMatchUiBinder extends UiBinder<Widget, AppShowMatch> {
	}

	@UiField
	Paragraph showMatchHTML;

	public AppShowMatch() {
		initWidget(uiBinder.createAndBindUi(this));

		setUp();
	}

	private void setUp() {
		String html = "<script src='./visorwebgl/jbinary.js'></script>" +
				"<script src='./visorwebgl/jdataview.js'></script>" +
				"<script src='./visorwebgl/javacup.js'></script>" + 
				"<script src='./visorwebgl/pixi.js'></script>" +
				"<script src='./visorwebgl/buzz.js'></script>" +
				"<script src='./visorwebgl/base.js'></script>" +
				"<script src='./visorwebgl/menu.js'></script>" +
				"<script src='./visorwebgl/anim.js'></script>" +
				"<div id='container'>" +
				"	<canvas id='gameCanvas'></canvas>" +
				"</div>" +
				"<script src=''./visorwebgl/game.js?5794153615065088'></script>";
		showMatchHTML.setHTML(html);
	}
}
