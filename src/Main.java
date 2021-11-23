import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.sql.*;

public class ScoreDTO {
	Connection con;
	Statement stmt;
	PreparedStatement pstmt;
	ResultSet rs;
	BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
	String sql;

	void getConnection() {
		String url = "jdbc:oracle:thin:@localhost:1521:xe";
		String id = "system";
		String pw = "1234";
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			System.out.println("====================================");
		} catch (Exception e) {
			System.out.println("오라클 연결실패");
			e.printStackTrace();
		} finally {
			System.out.println("오라클 연결성공");
			System.out.println("====================================");
		}

		try {
			con = DriverManager.getConnection(url, id, pw);
			System.out.println("====================================");
		} catch (Exception e) {
			System.out.println("데이터베이스 연결실패");
			System.out.println("====================================");
			e.printStackTrace();
		} finally {
			System.out.println("데이터베이스 연결성공");
			System.out.println("====================================");
		}
	}

	void insertStudent() {
		try {
			System.out.println("학생 이름을 입력하세요 : ");
			String STUDENT_NAME = bf.readLine();
			System.out.println("학생 번호를 입력하세요 : ");
			int STUDENT_NUMBER = Integer.parseInt(bf.readLine());
			System.out.println("학생 담임 사번을 입력하세요 : ");
			int TEACHER_NUMBER = Integer.parseInt(bf.readLine());

			sql = "INSERT INTO STUDENT_TABLE VALUES(?,?,?)";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, Integer.toString(STUDENT_NUMBER));
			pstmt.setString(2, STUDENT_NAME);
			pstmt.setString(3, Integer.toString(TEACHER_NUMBER));
			pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			System.out.println("완료");
		}
		System.out.println("====================================");
	}

	void SeclectName() {
		try {
			System.out.println("학생 이름을 입력하세요 : ");
			String STUDENT_NAME = bf.readLine();
			System.out.println("학생 번호을 입력하세요 : ");
			String STDDENT_NUM = bf.readLine();

			sql = "SELECT B.STUDENT_NAME, B.STUDENT_NUMBER, T.TEACHER_NAME FROM STUDENT_TABLE B, TEACHER_TABLE T "
					+ "WHERE B.TEACHER_NUMBER = T.TEACHER_NUMBER AND B.STUDENT_NAME = '"
					+ STUDENT_NAME + "'  AND B.STUDENT_NUMBER = " + STDDENT_NUM;

			System.out.println("====================================");
			stmt = con.createStatement();
			rs = stmt.executeQuery(sql);

			System.out.print("학생 이름\t학생번호\t학생담임\n");
			while (rs.next()) {
				System.out.print(rs.getString(1) + "\t");
				System.out.print(rs.getString(2) + "\t");
				System.out.print(rs.getString(3) + "\t\n");
			}
		}

		catch (Exception e) {
			e.printStackTrace();
		} finally {
			System.out.println("완료");
		}
		System.out.println("====================================");
	}

	void selectStudentAll() {
		try {

			System.out.print("담임사번 (전체를 보고싶으면 -1)\n");
			int num = Integer.parseInt(bf.readLine());
			if (num == -1)
				sql = "SELECT B.STUDENT_NUMBER, B.STUDENT_NAME, T.TEACHER_NAME FROM STUDENT_TABLE B, TEACHER_TABLE T WHERE B.TEACHER_NUMBER = T.TEACHER_NUMBER ORDER BY B.STUDENT_NUMBER";
			else
				sql = "SELECT B.STUDENT_NUMBER, B.STUDENT_NAME, T.TEACHER_NAME FROM STUDENT_TABLE B, TEACHER_TABLE T WHERE B.TEACHER_NUMBER = T.TEACHER_NUMBER AND T.TEACHER_NUMBER = "
						+ num + " ORDER BY STUDENT_NUMBER";
			System.out.println(sql);
			System.out.println("====================================");
			System.out.print("학번 \t");
			System.out.print("학생이름 \t");
			System.out.print("학생담임 \t\n");
			stmt = con.createStatement();
			rs = stmt.executeQuery(sql);
			while (rs.next()) {
				System.out.print(rs.getString(1) + "\t");
				System.out.print(rs.getString(2) + "\t");
				System.out.print(rs.getString(3) + "\t\n");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			System.out.println("완료");
		}
		System.out.println("====================================");
	}

	void updateName() {
		try {
			System.out.println("바꿀 학생 이름");
			String STUDENT_NAME = bf.readLine();
			System.out.println("바꿀 학생 번호");
			int STUDENT_NUMBER = Integer.parseInt(bf.readLine());
			System.out.println("새로운 학생 이름");
			String STUDENT_NAME2 = bf.readLine();
			sql = "UPDATE STUDENT_TABLE SET STUDENT_NAME = ? WHERE STUDENT_NAME = ? AND STUDENT_NUMBER = ?";

			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, STUDENT_NAME2);
			pstmt.setString(2, STUDENT_NAME);
			pstmt.setString(3, Integer.toString(STUDENT_NUMBER));

			pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			System.out.println("완료");
		}
		System.out.println("====================================");
	}

	void deletScore() {

		try {
			System.out.println("삭제할 학생 번호");
			int STUDENT_NUMBER = Integer.parseInt(bf.readLine());
			sql = "DELETE FROM SCORE_TABLE WHERE STUDENT_NUMBER = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, Integer.toString(STUDENT_NUMBER));
			pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			System.out.println("완료");
		}
		System.out.println("====================================");
	}

	void deleteStduent() {

		try {
			System.out.println("삭제할 학생 이름");
			String STUDENT_NAME = bf.readLine();
			System.out.println("삭제할 학생 번호");
			int STUDENT_NUMBER = Integer.parseInt(bf.readLine());
			try {
				sql = "DELETE FROM SCORE_TABLE WHERE STUDENT_NUMBER = ?";
				pstmt = con.prepareStatement(sql);
				pstmt.setString(1, Integer.toString(STUDENT_NUMBER));
				pstmt.executeUpdate();
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				System.out.println("완료");
			}
			System.out.println("====================================");
			sql = "DELETE FROM STUDENT_TABLE WHERE STUDENT_NAME = ? AND STUDENT_NUMBER = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, STUDENT_NAME);
			pstmt.setString(2, Integer.toString(STUDENT_NUMBER));
			pstmt.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			System.out.println("완료");
		}
		System.out.println("====================================");


	}



	void show_Rank() {
		try {
			System.out.println("시험연도를 입력하세요 예시 (2020년 > 20)");
			String TEST_DATE = bf.readLine();
			System.out.println("학기을 입력하세요 예시 ('1학기','2학기')");
			String TEST_TYPE = bf.readLine();
			System.out.println("중간/기말 을 입력하세요 예시 ('중간','기말')");
			String TEST_TYPE2 = bf.readLine();
			System.out.println("반 담임사번 (전체를 볼땐 -1)");
			int TEACHER_NUMBER = Integer.parseInt(bf.readLine());

			if (TEACHER_NUMBER == -1) {
				sql = "SELECT  B.STUDENT_NAME,A.STUDENT_NUMBER, ROUND((SUM(A.STUDENT_SCORE)/COUNT(*)),1),T.TEACHER_NAME "
						+ "FROM SCORE_TABLE A, STUDENT_TABLE B, TEACHER_TABLE T "
						+ "WHERE T.TEACHER_NUMBER = B.TEACHER_NUMBER AND B.STUDENT_NUMBER = A.STUDENT_NUMBER AND TEST_DATE= '"
						+ TEST_DATE
						+ "' AND A.TEST_TYPE = '"
						+ TEST_TYPE
						+ "' AND A.TEST_TYPE2 = '"
						+ TEST_TYPE2
						+ "' "
						+ "GROUP BY A.STUDENT_NUMBER, B.STUDENT_NAME, T.TEACHER_NAME ORDER BY ROUND((SUM(A.STUDENT_SCORE)/COUNT(*)),1) DESC";
			} else {
				sql = "SELECT  B.STUDENT_NAME,A.STUDENT_NUMBER, ROUND((SUM(A.STUDENT_SCORE)/COUNT(*)),1),T.TEACHER_NAME "
						+ "FROM SCORE_TABLE A, STUDENT_TABLE B, TEACHER_TABLE T "
						+ "WHERE T.TEACHER_NUMBER = "
						+ TEACHER_NUMBER
						+ " AND T.TEACHER_NUMBER = B.TEACHER_NUMBER AND B.STUDENT_NUMBER = A.STUDENT_NUMBER AND TEST_DATE = '"
						+ TEST_DATE
						+ "' AND A.TEST_TYPE = '"
						+ TEST_TYPE
						+ "' AND A.TEST_TYPE2 = '"
						+ TEST_TYPE2
						+ "' "
						+ "GROUP BY A.STUDENT_NUMBER, B.STUDENT_NAME, T.TEACHER_NAME ORDER BY ROUND((SUM(A.STUDENT_SCORE)/COUNT(*)),1) DESC";
			}

			System.out.println(sql);
			System.out.println("====================================");
			stmt = con.createStatement();
			rs = stmt.executeQuery(sql);

			System.out.print("학생이름\t학생 번호\t평균점수\t학생담임\n");
			while (rs.next()) {
				System.out.print(rs.getString(1) + "\t");
				System.out.print(rs.getString(2) + "\t");
				System.out.print(rs.getString(3) + "점\t");
				System.out.println(rs.getString(4) + "\t");
			}
		}

		catch (Exception e) {
			e.printStackTrace();
		} finally {
			System.out.println("완료");
		}
		System.out.println("====================================");
	}

	void insertScore() {
		try {
			System.out.println("학생 번호을 입력하세요 : ");
			int STUDENT_NAME = Integer.parseInt(bf.readLine());
			System.out.println("선생 번호를 입력하세요 : ");
			int STUDENT_NUMBER = Integer.parseInt(bf.readLine());
			System.out.println("과목을 입력하세요 : ('국어','영어','수학','한국사','일본어')");
			String SUBJECT = bf.readLine();
			System.out.println("학기를 입력하세요 : ('1학기','2학기')");
			String TEST_TYPE = bf.readLine();
			System.out.println("고사를 입력하세요 : ('중간','기말')");
			String TEST_TYPE2 = bf.readLine();
			System.out.println("점수를 입력하세요 : ");
			int STUDENT_SCORE = Integer.parseInt(bf.readLine());
			System.out.println("시험연도를 입력하세요 : (예시 2020 > 20)");
			String TEST_DATE = bf.readLine();

			sql = "INSERT INTO SCORE_TABLE VALUES(?,?,?,?,?,?,?)";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, Integer.toString(STUDENT_NAME));
			pstmt.setString(2, Integer.toString(STUDENT_NUMBER));
			pstmt.setString(3, SUBJECT);
			pstmt.setString(4, TEST_TYPE);
			pstmt.setString(5, TEST_TYPE2);
			pstmt.setString(6, Integer.toString(STUDENT_SCORE));
			pstmt.setString(7, TEST_DATE);
			pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			System.out.println("완료");
		}
		System.out.println("====================================");
	}

	void show_Solo_Rank() {
		try {
			System.out.println("학생 이름을 입력하세요 : ");
			String STUDENT_NAME = bf.readLine();
			System.out.println("학생 번호을 입력하세요 : ");
			String STDDENT_NUM = bf.readLine();

			sql = "SELECT B.STUDENT_NAME, S.SUBJECT, S.TEST_DATE, S.TEST_TYPE, S.TEST_TYPE2, S.STUDENT_SCORE,T.TEACHER_NAME FROM SCORE_TABLE S, STUDENT_TABLE B, TEACHER_TABLE T WHERE T.TEACHER_NUMBER = S.TEACHER_NUMBER AND S.STUDENT_NUMBER = B.STUDENT_NUMBER AND "
					+ "B.STUDENT_NAME = '"
					+ STUDENT_NAME
					+ "' AND S.STUDENT_NUMBER = " + STDDENT_NUM;

			System.out.println("====================================");
			stmt = con.createStatement();
			rs = stmt.executeQuery(sql);

			System.out.println("학생 이름\t과목\t시험연도\t1학기|2학기\t중간|기말\t점수\t학생담임\t");
			while (rs.next()) {
				System.out.print(rs.getString(1) + "\t");
				System.out.print(rs.getString(2) + "\t");
				System.out.print(rs.getString(3) + "\t");
				System.out.print(rs.getString(4) + "\t\t");
				System.out.print(rs.getString(5) + "\t");
				System.out.print(rs.getString(6) + "점\t");
				System.out.println(rs.getString(7) + "\t");
			}
		}

		catch (Exception e) {
			e.printStackTrace();
		} finally {
			System.out.println("완료");
		}
		System.out.println("====================================");
	}

	void show_Rank_Student() {
		try {
			System.out.println("시험연도를 입력하세요 예시 (2020년 > 20)");
			String TEST_DATE = bf.readLine();
			System.out.println("학기을 입력하세요 예시 ('1학기','2학기')");
			String TEST_TYPE = bf.readLine();
			System.out.println("중간/기말 을 입력하세요 예시 ('중간','기말')");
			String TEST_TYPE2 = bf.readLine();
			System.out.println("반 담임사번을 입력하세요 (전체를 보고싶으면 -1)");
			int TEACHER_NUMBER = Integer.parseInt(bf.readLine());

			if (TEACHER_NUMBER == -1) {
				sql = "SELECT SUBSTR(B.STUDENT_NAME,1,1) || LPAD('*',LENGTH(B.STUDENT_NAME)-2,'*') || SUBSTR(B.STUDENT_NAME, LENGTH(B.STUDENT_NAME), 1),A.STUDENT_NUMBER, ROUND((SUM(A.STUDENT_SCORE)/COUNT(*)),1),SUBSTR(T.TEACHER_NAME,1,1) || LPAD('*',LENGTH(T.TEACHER_NAME)-2,'*') || SUBSTR(T.TEACHER_NAME, LENGTH(T.TEACHER_NAME), 1) "
						+ "FROM SCORE_TABLE A, STUDENT_TABLE B, TEACHER_TABLE T "
						+ "WHERE T.TEACHER_NUMBER = B.TEACHER_NUMBER AND B.STUDENT_NUMBER = A.STUDENT_NUMBER AND TEST_DATE = '"
						+ TEST_DATE
						+ "' AND A.TEST_TYPE = '"
						+ TEST_TYPE
						+ "' AND A.TEST_TYPE2 = '"
						+ TEST_TYPE2
						+ "' "
						+ "GROUP BY A.STUDENT_NUMBER, B.STUDENT_NAME, T.TEACHER_NAME ORDER BY ROUND((SUM(A.STUDENT_SCORE)/COUNT(*)),1) DESC";
				System.out.println(sql);
			} else {
				sql = "SELECT SUBSTR(B.STUDENT_NAME,1,1) || LPAD('*',LENGTH(B.STUDENT_NAME)-2,'*') || SUBSTR(B.STUDENT_NAME, LENGTH(B.STUDENT_NAME), 1),A.STUDENT_NUMBER, ROUND((SUM(A.STUDENT_SCORE)/COUNT(*)),1),SUBSTR(T.TEACHER_NAME,1,1) || LPAD('*',LENGTH(T.TEACHER_NAME)-2,'*') || SUBSTR(T.TEACHER_NAME, LENGTH(T.TEACHER_NAME), 1) "
						+ "FROM SCORE_TABLE A, STUDENT_TABLE B, TEACHER_TABLE T "
						+ "WHERE T.TEACHER_NUMBER = "
						+ TEACHER_NUMBER
						+ " AND T.TEACHER_NUMBER = B.TEACHER_NUMBER AND B.STUDENT_NUMBER = A.STUDENT_NUMBER AND TEST_DATE = '"
						+ TEST_DATE
						+ "' AND A.TEST_TYPE = '"
						+ TEST_TYPE
						+ "' AND A.TEST_TYPE2 = '"
						+ TEST_TYPE2
						+ "' "
						+ "GROUP BY A.STUDENT_NUMBER, B.STUDENT_NAME, T.TEACHER_NAME ORDER BY ROUND((SUM(A.STUDENT_SCORE)/COUNT(*)),1) DESC";
			}

			System.out.println("====================================");
			stmt = con.createStatement();
			rs = stmt.executeQuery(sql);

			System.out.print("학생이름\t학생 번호\t평균점수\t학생담임\n");
			while (rs.next()) {
				System.out.print(rs.getString(1) + "\t");
				System.out.print(rs.getString(2) + "\t");
				System.out.print(rs.getString(3) + "점\t");
				System.out.println(rs.getString(4) + "\t");
			}
		}

		catch (Exception e) {
			e.printStackTrace();
		} finally {
			System.out.println("완료");
		}
		System.out.println("====================================");
	}

	void read_Mail() {
		try {
			System.out.println("사번을 입력하세요 : ");
			int TEACHER_NUMBER = Integer.parseInt(bf.readLine());

			sql = "SELECT S.STUDENT_NAME, M.SUBJECT, M.M_DATE FROM MAIL_TABLE M, STUDENT_TABLE S WHERE S.STUDENT_NUMBER = M.STUDENT_NUMBER AND S.TEACHER_NUMBER = "
					+ TEACHER_NUMBER;

			System.out.println("====================================");
			stmt = con.createStatement();
			rs = stmt.executeQuery(sql);

			System.out.println("학생 이름\t내용\t날짜\t");
			while (rs.next()) {
				System.out.print(rs.getString(1) + "\t");
				System.out.print(rs.getString(2) + "\t");
				System.out.println(rs.getString(3) + "\t");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			System.out.println("완료");
		}
		System.out.println("====================================");
	}

	void write_Mail() {
		try {
			System.out.println("학번을 입력하세요 : ");
			int STUDENT_NUMBER = Integer.parseInt(bf.readLine());
			System.out.println("받을 선생님의 사번 을 입력하세요 : ");
			int TEACHER_NUMBER = Integer.parseInt(bf.readLine());
			System.out.println("보낼 내용을 입력하세요 : ");
			String SUBJECT = bf.readLine();
			sql = "INSERT INTO MAIL_TABLE VALUES(?,?,?,SYSDATE)";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, Integer.toString(STUDENT_NUMBER));
			pstmt.setString(2, Integer.toString(TEACHER_NUMBER));
			pstmt.setString(3, SUBJECT);
			pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			System.out.println("완료");
		}
		System.out.println("====================================");
	}

	void delete_Mail() {

		try {
			System.out.println("삭제할 메일 학생번호");
			int STUDENT_NUMBER = Integer.parseInt(bf.readLine());
			sql = "DELETE FROM MAIL_TABLE WHERE STUDENT_NUMBER = ?";

			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, Integer.toString(STUDENT_NUMBER));
			pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			System.out.println("완료");
		}
		System.out.println("====================================");
	}

	void insertTeacher() {
		try {
			System.out.println("추가할 선생님 사번을 입력하세요 : ");
			int TEACHER_NUMBER = Integer.parseInt(bf.readLine());
			System.out.println("추가할 선생님 이름을 입력하세요 : ");
			String TEACHER_NAME = bf.readLine();

			sql = "INSERT INTO TEACHER_TABLE VALUES(?,?)";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, Integer.toString(TEACHER_NUMBER));
			pstmt.setString(2, TEACHER_NAME);
			pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			System.out.println("완료");
		}
		System.out.println("====================================");
	}

	void deleteTeacher() {
		try {
			System.out.println("삭제할 선생님 사번을 입력하세요 : ");
			int TEACHER_NUMBER = Integer.parseInt(bf.readLine());
			System.out.println("삭제할선생님 이름을 입력하세요 : ");
			String TEACHER_NAME = bf.readLine();

			sql = "DELETE TEACHER_TABLE WHERE TEACHER_NUMBER = ? AND TEACHER_NAME = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, Integer.toString(TEACHER_NUMBER));
			pstmt.setString(2, TEACHER_NAME);
			pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			System.out.println("완료");
		}
		System.out.println("====================================");
	}

	void showTeacherTalbe() {
		sql = "SELECT * FROM TEACHER_TABLE";
		System.out.println("====================================");
		try {
			stmt = con.createStatement();
			rs = stmt.executeQuery(sql);
			System.out.print("사번\t선생님이름\t\n");

			while (rs.next()) {
				System.out.print(rs.getString(1) + "\t");
				System.out.print(rs.getString(2) + "\t\n");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			System.out.println("완료");
		}
		System.out.println("====================================");
	}

	void showStudentTalbe() {
		sql = "SELECT * FROM STUDENT_TABLE";
		System.out.println("====================================");
		try {
			stmt = con.createStatement();
			rs = stmt.executeQuery(sql);
			System.out.print("학생번호\t학생이름\t담임사번\t\n");
			while (rs.next()) {
				System.out.print(rs.getString(1) + "\t");
				System.out.print(rs.getString(2) + "\t");
				System.out.print(rs.getString(3) + "\t\n");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			System.out.println("완료");
		}
		System.out.println("====================================");
	}

	void showScoreTalbe() {
		sql = "SELECT * FROM SCORE_TABLE";
		System.out.println("====================================");
		try {
			stmt = con.createStatement();
			rs = stmt.executeQuery(sql);
			System.out.print("학생번호\t학생담임번호\t과목\t학기\t중간|기말\t성적\t시험연도\t\n");
			while (rs.next()) {
				System.out.print(rs.getString(1) + "\t");
				System.out.print(rs.getString(2) + "\t");
				System.out.print(rs.getString(3) + "\t");
				System.out.print(rs.getString(4) + "\t");
				System.out.print(rs.getString(5) + "\t");
				System.out.print(rs.getString(6) + "\t");
				System.out.print(rs.getString(7) + "\t\n");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			System.out.println("완료");
		}
		System.out.println("====================================");
	}

	void disConnection() {
		try {
			if (!con.isClosed()) {
				System.out.println("====================================");
				System.out.println("데이터베이스 연결 종료");
				System.out.println("====================================");
				con.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
