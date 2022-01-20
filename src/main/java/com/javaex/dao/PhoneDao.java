package com.javaex.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.javaex.Vo.PersonVo;

@Repository // 스프링에서 자동으로 꺼내쓸때 필요
public class PhoneDao {

	@Autowired
	private SqlSession sqlSession; //sql쿼리를 불러오는 코드
	
	//전체 리스트 가져오기
	public List<PersonVo> getPersonList() {
		
		System.out.println("PhoneDao.personList()");
		
		List<PersonVo>  personList = sqlSession.selectList("phonebook.selectList");
		System.out.println(personList);
		return personList;
	}
	
	
	
	//전화번호 추가
	
	public int personInsert(PersonVo personVo) {
		
		
		System.out.println("PhoneDao.personInsert()");
		
		
		int count = sqlSession.insert("phonebook.insert",personVo);
		
		System.out.println(count+"건 추가");
		
		return count;
		/*
		 이렇게만 적어도 가능
		return sqlSession.insert("phonebook.insert",personVo);
		*/
		
	}
	
	//사람 삭제
	
	public int personDelete(int personId) {
		
		System.out.println("PhoneDao.personDelete");
		
		int count = sqlSession.delete("phonebook.delete", personId);
		
		System.out.println(count+"건 삭제");
		
		return count;
	}
	
	
	//사람 수정
	public int personUpdate(PersonVo personVo) {
		
		System.out.println("PhoneDao.personUpdate");
		System.out.println(personVo);
		int count = sqlSession.update("phonebook.Update",personVo);
		System.out.println(count+"건수정 했습니다.");
		return count;
	}
	
	//사랑 한명 불러오기
	
	public PersonVo getPerson(int personId) {
		
		System.out.println("PhoneDao.getperson");
		
		PersonVo personvo ;
		
		personvo = sqlSession.selectOne("phonebook.getperson", personId);
		
		System.out.println(personvo+"1명 불러오기");
		
		return personvo;
	}
	
/*
	private void getConnection() {
		try {
			// 1. JDBC 드라이버 (Oracle) 로딩
			

			// 2. Connection 얻어오기
			conn = sqlSession.getConnection();
			// System.out.println("접속성공");
		} catch (SQLException e) {
			System.out.println("error:" + e);
		}
	}

	public void close() {
		// 5. 자원정리
		try {
			if (rs != null) {
				rs.close();
			}
			if (pstmt != null) {
				pstmt.close();
			}
			if (conn != null) {
				conn.close();
			}
		} catch (SQLException e) {
			System.out.println("error:" + e);
		}
	}

	// 사람 추가
	public int personInsert(PersonVo personVo) {
		int count = 0;
		getConnection();

		try {

			// 3. SQL문 준비 / 바인딩 / 실행
			String query = ""; // 쿼리문 문자열만들기, ? 주의
			query += " INSERT INTO person ";
			query += " VALUES (seq_person_id.nextval, ?, ?, ?) ";
			// System.out.println(query);

			pstmt = conn.prepareStatement(query); // 쿼리로 만들기

			pstmt.setString(1, personVo.getName()); // ?(물음표) 중 1번째, 순서중요
			pstmt.setString(2, personVo.getHp()); // ?(물음표) 중 2번째, 순서중요
			pstmt.setString(3, personVo.getCompany()); // ?(물음표) 중 3번째, 순서중요

			count = pstmt.executeUpdate(); // 쿼리문 실행

			// 4.결과처리
			System.out.println("[" + count + "건 추가되었습니다.]");

		} catch (SQLException e) {
			System.out.println("error:" + e);
		}
		close();
		return count;
	}

	// 사람 리스트(검색안할때)
	public List<PersonVo> getPersonList() {
		return getPersonList("");
	}

	// 사람 리스트(검색할때)
	public List<PersonVo> getPersonList(String keword) {
		List<PersonVo> personList = new ArrayList<PersonVo>();

		getConnection();

		try {

			// 3. SQL문 준비 / 바인딩 / 실행 --> 완성된 sql문을 가져와서 작성할것
			String query = "";
			query += " select  person_id, ";
			query += "         name, ";
			query += "         hp, ";
			query += "         company ";
			query += " from person";

			if (keword != "" || keword == null) {
				query += " where name like ? ";
				query += " or hp like  ? ";
				query += " or company like ? ";
				pstmt = conn.prepareStatement(query); // 쿼리로 만들기

				pstmt.setString(1, '%' + keword + '%'); // ?(물음표) 중 1번째, 순서중요
				pstmt.setString(2, '%' + keword + '%'); // ?(물음표) 중 2번째, 순서중요
				pstmt.setString(3, '%' + keword + '%'); // ?(물음표) 중 3번째, 순서중요
			} else {
				pstmt = conn.prepareStatement(query); // 쿼리로 만들기
			}

			rs = pstmt.executeQuery();

			// 4.결과처리
			while (rs.next()) {
				int personId = rs.getInt("person_id");
				String name = rs.getString("name");
				String hp = rs.getString("hp");
				String company = rs.getString("company");

				PersonVo personVo = new PersonVo(personId, name, hp, company);
				personList.add(personVo);
			}

		} catch (SQLException e) {
			System.out.println("error:" + e);
		}

		close();

		return personList;

	}

	// 사람 수정
	public int personUpdate(PersonVo personVo) {
		int count = 0;
		getConnection();

		try {

			// 3. SQL문 준비 / 바인딩 / 실행
			String query = ""; // 쿼리문 문자열만들기, ? 주의
			query += " update person ";
			query += " set name = ? , ";
			query += "     hp = ? , ";
			query += "     company = ? ";
			query += " where person_id = ? ";

			pstmt = conn.prepareStatement(query); // 쿼리로 만들기

			pstmt.setString(1, personVo.getName()); // ?(물음표) 중 1번째, 순서중요
			pstmt.setString(2, personVo.getHp()); // ?(물음표) 중 2번째, 순서중요
			pstmt.setString(3, personVo.getCompany()); // ?(물음표) 중 3번째, 순서중요
			pstmt.setInt(4, personVo.getPersonId()); // ?(물음표) 중 4번째, 순서중요

			count = pstmt.executeUpdate(); // 쿼리문 실행

			// 4.결과처리
			// System.out.println(count + "건 수정되었습니다.");

		} catch (SQLException e) {
			System.out.println("error:" + e);
		}

		close();
		return count;
	}

	// 사람 삭제
	public int personDelete(int personId) {
		int count = 0;
		getConnection();

		try {
			// 3. SQL문 준비 / 바인딩 / 실행
			String query = ""; // 쿼리문 문자열만들기, ? 주의
			query += " delete from person ";
			query += " where person_id = ? ";
			pstmt = conn.prepareStatement(query); // 쿼리로 만들기

			pstmt.setInt(1, personId);// ?(물음표) 중 1번째, 순서중요

			count = pstmt.executeUpdate(); // 쿼리문 실행

			// 4.결과처리
			// System.out.println(count + "건 삭제되었습니다.");

		} catch (SQLException e) {
			System.out.println("error:" + e);
		}

		close();
		return count;
	}

	// 사람 한명만 불러오기
	public PersonVo getPerson(int personId) {

		PersonVo personVo = null;

		getConnection();

		try {

			// 3. SQL문 준비 / 바인딩 / 실행 --> 완성된 sql문을 가져와서 작성할것
			String query = "";
			query += " select  person_id, ";
			query += "         name, ";
			query += "         hp, ";
			query += "         company ";
			query += " from person ";
			query += " where person_id = ? ";

			pstmt = conn.prepareStatement(query); // 쿼리로 만들기

			pstmt.setInt(1, personId); // ?(물음표) 중 1번째, 순서중요

			rs = pstmt.executeQuery();

			// 4.결과처리
			rs.next();
			int id = rs.getInt("person_id");
			String name = rs.getString("name");
			String hp = rs.getString("hp");
			String company = rs.getString("company");

			personVo = new PersonVo(id, name, hp, company);

		} catch (SQLException e) {
			System.out.println("error:" + e);
		}

		close();

		return personVo;

	}
}
*/
	
}