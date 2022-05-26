package memo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.tomcat.dbcp.dbcp2.PStmtKey;

import utility.DBClose;
import utility.DBOpen;

public class memoDAO {
  
  public  void upAnsum(Map map) {
    Connection con = DBOpen.getConnection();
    PreparedStatement pstmt = null;
    StringBuffer sql = new StringBuffer();
sql.append("   UPDATE memo    ");
sql.append("  SET    ");
sql.append("  ansnum=ansnum+1   ");
sql.append("  WHERE memono = ?   ");
    
int grpno = (int)map.get("grpno");
int ansnum = (int)map.get("ansnum");

    try {
      pstmt = con.prepareStatement(sql.toString());
      pstmt.setInt(1, grpno);
      pstmt.setInt(2, ansnum);
      
      pstmt.executeUpdate();
    } catch (SQLException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }finally {
      DBClose.close(pstmt, con);
    }
    
    
  
  }
  
  public boolean createReply(memoDTO dto) {
    boolean flag = false;
    Connection con = DBOpen.getConnection();
    PreparedStatement pstmt = null;
    StringBuffer sql = new StringBuffer();
    sql.append(" INSERT INTO memo( wname, title, content, passwd, wdate, grpno, indent, ansnum)   ");
    sql.append("  VALUES(?, ?, ?,?, sysdate(),?,?,?)  ");
    
    
    try {
      pstmt = con.prepareStatement(sql.toString());
      pstmt.setString(1, dto.getWname());
      pstmt.setString(2, dto.getTitle());
      pstmt.setString(3, dto.getContent());
      pstmt.setString(4, dto.getPasswd());
      pstmt.setInt(5, dto.getGrpno());
      pstmt.setInt(6, dto.getIndent()+1);
      pstmt.setInt(7, dto.getAnsnum()+1);
      
      int cnt = pstmt.executeUpdate();
      
      if(cnt>0) flag = true;
      
    } catch (SQLException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    } finally {
      DBClose.close(pstmt, con);
    }
    
    return flag;
  }

 
  
  public memoDTO readReply(int memono) {
memoDTO dto = null;
Connection con = DBOpen.getConnection();
PreparedStatement pstmt = null;
ResultSet rs = null;

StringBuffer sql = new StringBuffer();
sql.append(" select memono,  title, grpno, indent, ansnum ");
sql.append(" from memo ");
sql.append(" where memono =?  ");

try {
  pstmt = con.prepareStatement(sql.toString());
  pstmt.setInt(1, memono);
  
  rs = pstmt.executeQuery();
  
  if (rs.next()) {
    dto = new memoDTO();
    dto.setMemono(rs.getInt("memono"));
    dto.setTitle(rs.getString("title"));
    dto.setGrpno(rs.getInt("grpno"));
    dto.setIndent(rs.getInt("indent"));
    dto.setAnsnum(rs.getInt("ansnum"));
  }
} catch (SQLException e) {
  // TODO Auto-generated catch block
  e.printStackTrace();
}finally {
  DBClose.close(rs, pstmt, con);
}


return dto;
  }

  
  
  
  public boolean delete(int bbsno) {
    boolean flag= false;
    Connection con = DBOpen.getConnection();
    PreparedStatement pstmt =null;
    
    StringBuffer sql = new StringBuffer();
sql.append(" DELETE FROM memo  ");
sql.append(" WHERE memono=?  ");
  
  try {
    pstmt = con.prepareStatement(sql.toString());
    pstmt.setInt(1, bbsno);
    
    int cnt = pstmt.executeUpdate();
    if(cnt>0) flag=true;
  } catch (SQLException e) {
    // TODO Auto-generated catch block
    e.printStackTrace();
  }finally {
    DBClose.close(pstmt, con);
  }
  
  return flag;
  }
  public boolean passCheck(Map map) {
    boolean flag = false;
    Connection con = DBOpen.getConnection();
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    int memono =(int)map.get("memono");
    String passwd = (String) map.get("passwd");
    
    StringBuffer sql = new StringBuffer();
sql.append("  select count(memono) as cnt  ");
sql.append(" from memo   ");
sql.append("  where memono =? ");
sql.append("  and passwd =?  ");

try {
  pstmt=con.prepareStatement(sql.toString());
  pstmt.setInt(1, memono);
  pstmt.setString(2, passwd);
  
  rs = pstmt.executeQuery();
  rs.next();
  int cnt = rs.getInt("cnt");
  
  if(cnt>0) flag =true;//올바른 패스워드
} catch (SQLException e) {
  // TODO Auto-generated catch block
  e.printStackTrace();
}finally {
  DBClose.close(rs, pstmt, con);
}

return flag;    
  }
  
  public boolean update(memoDTO dto) {
    boolean flag = false;
    Connection con = DBOpen.getConnection();
    PreparedStatement pstmt = null;
    
    StringBuffer sql = new StringBuffer();
sql.append(" update memo ");
sql.append(" set wname=?, ");
sql.append(" title = ?, ");
sql.append(" content = ? ");
sql.append(" where memono =? ");
    
try {
  pstmt = con.prepareStatement(sql.toString());
pstmt.setString(1, dto.getWname());
pstmt.setString(2, dto.getTitle());
pstmt.setString(3, dto.getContent());
pstmt.setInt(4, dto.getMemono());
int cnt = pstmt.executeUpdate();
if (cnt > 0)
  flag = true;

} catch (SQLException e) {
 // TODO Auto-generated catch block
 e.printStackTrace();
}finally {
 DBClose.close( pstmt, con);
}
return flag;
  }
  
  public memoDTO read(int memono) {
memoDTO dto = null;
Connection con = DBOpen.getConnection();
PreparedStatement pstmt = null;
ResultSet rs = null;

StringBuffer sql = new StringBuffer();
sql.append(" select memono, wname, title, content, viewcnt, wdate ");
sql.append(" from memo ");
sql.append(" where memono =?  ");

try {
  pstmt = con.prepareStatement(sql.toString());
  pstmt.setInt(1, memono);
  
  rs = pstmt.executeQuery();
  
  if (rs.next()) {
    dto = new memoDTO();
    dto.setMemono(rs.getInt("memono"));
    dto.setWname(rs.getString("wname"));
    dto.setTitle(rs.getString("title"));
    dto.setContent(rs.getString("content"));
    dto.setViewcnt(rs.getInt("viewcnt"));
    dto.setWdate(rs.getString("wdate"));
  }
} catch (SQLException e) {
  // TODO Auto-generated catch block
  e.printStackTrace();
}finally {
  DBClose.close(rs, pstmt, con);
}


return dto;
  }
  public void upViewcnt(int bbsno) {
    Connection con = DBOpen.getConnection();
    PreparedStatement pstmt = null;
    
    StringBuffer sql = new StringBuffer();
    sql.append(" UPDATE memo   ");
    sql.append(" SET viewcnt=viewcnt+1  ");
    sql.append(" WHERE memono = ?  ");
    
    try {
      pstmt = con.prepareStatement(sql.toString());
      pstmt.setInt(1, bbsno);
      
      pstmt.executeUpdate();
    } catch (SQLException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }finally {
      DBClose.close(pstmt, con);
    }
    
    
  }
  public int total(Map map) { //col,word
    int total = 0;
    Connection con = DBOpen.getConnection();
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    
    String col = (String)map.get("col"); //검색컬럼 : wname, title, content, title_content
    String word = (String)map.get("word"); //사용자가 입력한 단어 : 홍, test, 
    
    StringBuffer sql = new StringBuffer();
    sql.append(" select count(*) from memo ");
    
    if(word.trim().length() >0 && col.equals("title_content")) {
      sql.append(" where title like concat('%',?,'%') ");
      sql.append(" or content like concat('%',?,'%') ");
    }else if(word.trim().length() > 0) {
      sql.append(" where "+ col +" like concat('%',?,'%')" );
    }
    
    try {
      pstmt = con.prepareStatement(sql.toString());
      if(word.trim().length() >0 && col.equals("title_content")) {
        pstmt.setString(1, word);
        pstmt.setString(2, word);
      }else if(word.trim().length() > 0) {
        pstmt.setString(1, word);
      }
      
      rs = pstmt.executeQuery();
      
      if(rs.next()) {
        total = rs.getInt(1);
      }
      
    } catch (SQLException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    } finally {
      DBClose.close(rs, pstmt, con);
    }
    
    return total;
  }
  public List<memoDTO> list(Map map) {
    List<memoDTO > list = new ArrayList<memoDTO>();
    Connection con = DBOpen.getConnection();
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    
    String col = (String)map.get("col"); //검색컬럼 : wname, title, content, title_content
    String word = (String)map.get("word"); //사용자가 입력한 단어 : 홍, test, 
    int sno = (int)map.get("sno"); //레코드 시작위치
    int eno = (int)map.get("eno"); //가져올 건수
    
    StringBuffer sql = new StringBuffer();
    sql.append(" select memono, wname, title, viewcnt, wdate, grpno, indent, ansnum ");
    sql.append(" from memo ");
    
    if(word.trim().length() >0 && col.equals("title_content")) {
      sql.append(" where title like concat('%',?,'%') ");
      sql.append(" or content like concat('%',?,'%') ");
    }else if(word.trim().length() > 0) {
      sql.append(" where "+ col +" like concat('%',?,'%')" );
    }
    
    sql.append(" order by grpno desc, ansnum  ");
    sql.append(" limit ?, ? ");
    
    int i =0;
    try {
      pstmt = con.prepareStatement(sql.toString());
      if(word.trim().length() >0 && col.equals("title_content")) {
        pstmt.setString(++i, word);
        pstmt.setString(++i, word);
      }else if(word.trim().length() > 0) {
        pstmt.setString(++i, word);
      }
      
      pstmt.setInt(++i, sno);
      pstmt.setInt(++i, eno);
      
      rs = pstmt.executeQuery();
      
      while(rs.next()) {
        memoDTO dto = new memoDTO();
        dto.setMemono(rs.getInt("memono"));
        dto.setWname(rs.getString("wname"));
        dto.setTitle(rs.getString("title"));
        dto.setViewcnt(rs.getInt("viewcnt"));
        dto.setWdate(rs.getString("wdate"));
        dto.setGrpno(rs.getInt("grpno"));
        dto.setIndent(rs.getInt("indent"));
        dto.setAnsnum(rs.getInt("ansnum"));
        
        list.add(dto);
      }
      
    } catch (SQLException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    } finally {
      DBClose.close(rs, pstmt, con);
    }
    
    return list;
  }
  
  public boolean create(memoDTO dto) {
    boolean flag = false;
    Connection con = DBOpen.getConnection();
    PreparedStatement pstmt = null;
    StringBuffer sql = new StringBuffer();
    sql.append(" insert into memo (wname, title, content, wdate, grpno, passwd)  ");
    sql.append(" values (?,?,?,sysdate(),  ");
    sql.append(" (select ifnull(max(grpno),0) + 1 from memo m), ?)  ");
    
    
    
    
    try {
      pstmt = con.prepareStatement(sql.toString());
      pstmt.setString(1, dto.getWname());
      pstmt.setString(2, dto.getTitle());
      pstmt.setString(3, dto.getContent());
      pstmt.setString(4, dto.getPasswd());
      
      int cnt = pstmt.executeUpdate();
      
      if(cnt>0) flag = true;
      
    } catch (SQLException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    } finally {
      DBClose.close(pstmt, con);
    }
    
    return flag;
  }
}
