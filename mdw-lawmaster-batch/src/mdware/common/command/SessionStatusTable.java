package mdware.common.command;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpSessionListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSession;

import jp.co.vinculumjapan.stc.log.StcLog;


/**
 * <p>タイトル: SessionStatusTable クラス</p>
 * <p>説明: クラス概要</p>
 * <p>著作権: Copyright (c) 2002-2003</p>
 * <p>会社名: Vinculum Japan Corporation</p>
 * @author M.Ashizawa
 * @version 1.0 (2003.01.27) 初版作成
 */
public class SessionStatusTable implements HttpSessionListener {

	// StcLog インスタンス。
	private static StcLog stcLog = StcLog.getInstance();

	// セションの情報を保持する Map。
	private static Map sessionInfoMap_ = new HashMap();

	// セションの情報を保持する HttpSession 配列。
	private static Object[] sessionInfoArray_ = new HttpSession[]{};

	/**
	 *
	 * @param se
	 */
	public void sessionCreated(HttpSessionEvent se) {
		addSession(se.getSession());
		stcLog.getLog().debug("セッションが作成されました。" + se.getSession().getId());
	}

	/**
	 *
	 * @param se
	 */
	public void sessionDestroyed(HttpSessionEvent se) {
		removeSession(se.getSession());
		stcLog.getLog().debug("セッションが切断されました。" + se.getSession().getId());
	}

	/**
	 *
	 * @param session
	 */
	private synchronized static void addSession(HttpSession session) {
		sessionInfoMap_.put(session.getId(), session);
		Collection coll = sessionInfoMap_.values();
		if (coll != null) {
			sessionInfoArray_ = coll.toArray();
		} else {
			sessionInfoArray_ = new HttpSession[]{};
		}
	}

	/**
	 *
	 * @param session
	 */
	private synchronized static void removeSession(HttpSession session) {
		sessionInfoMap_.remove(session.getId());
		Collection coll = sessionInfoMap_.values();
		if (coll != null) {
			sessionInfoArray_ = coll.toArray();
		} else {
			sessionInfoArray_ = new HttpSession[]{};
		}
	}


	/**
	 * 現在の全セション数（接続クライアント数）を返します。
	 *
	 * @return  全セション数
	 */
	public static int getNumOfSessions() {
		return sessionInfoArray_.length;
	}

	/**
	 * 現在の全てのセションの中から index で指定された HttpSession オブジェクトを返します。
	 *
	 * @param index
	 * @return  HttpSession オブジェクト
	 */
	public static HttpSession getSession(int index) {
		return (HttpSession)sessionInfoArray_[index];
	}

}