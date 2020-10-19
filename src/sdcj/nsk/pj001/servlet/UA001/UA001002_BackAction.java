package sdcj.nsk.pj001.servlet.UA001;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import sdcj.nsk.pj001.dao.UA001001DetailDao;
import sdcj.nsk.pj001.dto.AccountTableDto;

/**
 * Servlet implementation class UA001002_BackAction
 */
@WebServlet("/UA001002_BackAction")
public class UA001002_BackAction extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public UA001002_BackAction() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// フォワード先
		String url = "./jsp/ua001/ua001001.jsp";

		// ログインしているユーザー情報を取得
		var session = request.getSession(true);
		var loginUser = (AccountTableDto)session.getAttribute("loginUser");

		if (loginUser == null || loginUser.getLoginId().isEmpty()) {
			// 未ログイン
			response.sendRedirect("jsp/cm001/cm001001.jsp");
			return;
		}

		// ログイン済み
		try {
			// ページ番号
			int pageNum = 1;

			// 売上伝票情報Viewのリストを取得
			var ua001001DetailList = UA001001DetailDao.selectByCondition("000001", "999999", "00010101", "99991231", pageNum);

			// リクエストスコープに投げる
			request.setAttribute("ua001001DetailList", ua001001DetailList);
			request.setAttribute("page_num", pageNum);

			// jspをフォワード
			request.getRequestDispatcher(url).forward(request, response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
