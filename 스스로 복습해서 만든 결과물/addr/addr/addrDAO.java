package addr;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import bbs.BbsDTO;
import utility.DBClose;
import utility.DBOpen;

public class addrDAO {

  
  public boolean delete(int addressnum) {
    boolean flag= false;
    Connection con = DBOpen.getConnection();
    PreparedStatement pstmt =null;
    
    StringBuffer sql = new StringBuffer();
sql.append(" DELETE FROM address  ");
sql.append(" WHERE addressnum=?  ");
  
  try {
    pstmt = con.prepareStatement(sql.toString());
    pstmt.setInt(1, addressnum);
    
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
  
  

  
  
  
  
  
  
  
  
  
  public boolean update(addrDTO dto) {
    boolean flag = false;
    Connection con = DBOpen.getConnection();
    PreparedStatement pstmt = null;
    
    StringBuffer sql = new StringBuffer();
sql.append(" update address ");
sql.append(" set name=?, ");
sql.append(" handphone = ?, ");
sql.append(" zipcode= ?, ");
sql.append(" address= ?, ");
sql.append(" address2  = ? ");
sql.append(" where addressnum =? ");
    
try {
  pstmt = con.prepareStatement(sql.toString());
pstmt.setString(1, dto.getName());
pstmt.setString(2, dto.getHandphone());
pstmt.setString(3, dto.getZipcode());
pstmt.setString(4, dto.getAddress());
pstmt.setString(5, dto.getAddress2());
pstmt.setInt(6, dto.getAddressnum());
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
  
  
  
  
  
  
  public addrDTO read(int addressnum) {
    addrDTO dto = null;
    Connection con = DBOpen.getConnection();
    PreparedStatement pstmt = null;
    ResultSet rs = null;

    StringBuffer sql = new StringBuffer();
    sql.append("   SELECT addressnum, name, handphone, zipcode ,address ,address2 ");
    sql.append("     FROM address  ");
    sql.append("     WHERE addressnum=?  ");

    try {
      pstmt = con.prepareStatement(sql.toString());
      pstmt.setInt(1, addressnum);
      
      rs = pstmt.executeQuery();
      
      if (rs.next()) {
        dto = new addrDTO();
        /*
         * dto.setAddressnum(rs.getInt("bbsno")); dto.setWname(rs.getString("wname"));
         * dto.setTitle(rs.getString("title")); dto.setContent(rs.getString("content"));
         * dto.setViewcnt(rs.getInt("viewcnt")); dto.setWdate(rs.getString("wdate"));
         */
        dto.setAddressnum(rs.getInt("addressnum"));
        dto.setName(rs.getString("name"));
        dto.setHandphone(rs.getString("handphone"));
        dto.setZipcode(rs.getString("zipcode"));
        dto.setAddress(rs.getString("address"));
        dto.setAddress2(rs.getString("address2"));
      }
    } catch (SQLException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }finally {
      DBClose.close(rs, pstmt, con);
    }


    return dto;
      }
  
  
  
  
  public int total(Map map) { //col,word
    int total = 0;
    Connection con = DBOpen.getConnection();
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    
    String col = (String)map.get("col"); //검색컬럼 : wname, title, content, title_content
    String word = (String)map.get("word"); //사용자가 입력한 단어 : 홍, test, 
    
    StringBuffer sql = new StringBuffer();
    sql.append(" select count(*) from address ");
    
    if(word.trim().length() >0 && col.equals("name_handphone")) {
      sql.append(" where name like concat('%',?,'%') ");
      sql.append(" or handphone like concat('%',?,'%') ");
    }else if(word.trim().length() > 0) {
      sql.append(" where "+ col +" like concat('%',?,'%')" );
    }
    
    try {
      pstmt = con.prepareStatement(sql.toString());
      if(word.trim().length() >0 && col.equals("name_handphone")) {
        pstmt.setString(1, word);
        pstmt.setString(2, word);
      }else if(word.trim().length() > 0) {
        pstmt.setString(1, word);
      }
      
     System.out.println(sql);
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
  
  
  
  
  
  public List<addrDTO> list(Map map) {
    List<addrDTO > list = new ArrayList<addrDTO>();
    Connection con = DBOpen.getConnection();
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    
    String col = (String)map.get("col"); //검색컬럼 : wname, title, content, title_content
    String word = (String)map.get("word"); //사용자가 입력한 단어 : 홍, test, 
    int sno = (int)map.get("sno"); //레코드 시작위치
    int eno = (int)map.get("eno"); //가져올 건수
    
    StringBuffer sql = new StringBuffer();
    sql.append("  SELECT addressnum, name, handphone,address ");
    sql.append("     FROM address  ");
    
    if(word.trim().length() >0 && col.equals("name_handphone")) {
     sql.append(" where name like concat('%',?,'%') ");
     sql.append(" or handphone like concat('%',?,'%') ");
    }else if(word.trim().length() > 0) {
      sql.append(" where "+ col +" like concat('%',?,'%')" );
    }
    
    sql.append(" order by addressnum desc  ");
    sql.append(" limit ?, ? ");
    
    int i =0;
    try {
      pstmt = con.prepareStatement(sql.toString());
      if(word.trim().length() >0 && col.equals("name_handphone")) {
        pstmt.setString(++i, word);
        pstmt.setString(++i, word);
      }else if(word.trim().length() > 0) {
        pstmt.setString(++i, word);
     }
      
            pstmt.setInt(++i, sno);
            pstmt.setInt(++i, eno);
            
      System.out.println(sql);
      rs = pstmt.executeQuery();
      
      while(rs.next()) {
        addrDTO dto = new addrDTO();
        dto.setAddressnum(rs.getInt("addressnum"));
        dto.setName(rs.getString("name"));
        dto.setHandphone(rs.getString("handphone"));
        //dto.setZipcode(rs.getString("zipcode"));
        dto.setAddress(rs.getString("address"));
       // dto.setAddress2(rs.getString("address2"));
        
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

  
  
  
  

  public boolean create(addrDTO dto) {
    boolean flag = false;
    Connection con = DBOpen.getConnection();
    PreparedStatement pstmt = null;
    StringBuffer sql = new StringBuffer();
    sql.append(" INSERT INTO address(name, handphone, zipcode, address,address2)     ");
    sql.append("     VALUES(?, ?, ?,?,?)   ");
    
    
    try {
      pstmt = con.prepareStatement(sql.toString());
      pstmt.setString(1, dto.getName());
      pstmt.setString(2, dto.getHandphone());
      pstmt.setString(3, dto.getZipcode());
      pstmt.setString(4, dto.getAddress());
      pstmt.setString(5, dto.getAddress2());
      
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

