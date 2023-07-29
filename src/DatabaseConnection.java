import java.sql.*;

public class DatabaseConnection {
    public static Connection conn;
    private int gameModeIndex, boardSizeIndex;
    private String mInfoTimer, mInfoBoard, mInfoWin;

    public DatabaseConnection(){
        //database connection
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            conn = DriverManager.getConnection("jdbc:mysql://localhost/tictactoe",
                    "root",
                    "");

            if (conn != null){
                System.out.println("...MySQL server connected.");
            }else{
                System.out.println("Failed to make connection!");
            }

        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }

        //retrieve all the setting in the database
        try{
            String query = "SELECT * FROM tictactoesetting WHERE setting_id = 1";
            PreparedStatement ps = DatabaseConnection.conn.prepareStatement(query);
            ResultSet rs = ps.executeQuery();

            if (rs.next()){
                gameModeIndex = rs.getInt("game_mode");
                boardSizeIndex = rs.getInt("board_size");
                mInfoTimer = rs.getString("minfo_timer");
                mInfoBoard = rs.getString("minfo_board");
                mInfoWin = rs.getString("minfo_win");

                System.out.println(boardSizeIndex);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    //getter
    public int getGameModeIndex(){return gameModeIndex;}
    public int getBoardSizeIndex(){return boardSizeIndex;}
    public String getmInfoTimer(){return mInfoTimer;}
    public String getmInfoBoard(){return mInfoBoard;}
    public String getmInfoWin(){return mInfoWin;}

}
