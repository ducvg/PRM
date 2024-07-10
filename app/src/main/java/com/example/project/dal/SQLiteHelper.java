package com.example.project.dal;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.project.model.Category;
import com.example.project.model.Task;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class SQLiteHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "Project.sqlite";
    private static int DATABASE_VERSION = 7;
    public SQLiteHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(@NonNull SQLiteDatabase db) {
        String sqlCreateCategoryTable = "CREATE TABLE category (" +
                "    CategoryId INTEGER PRIMARY KEY," +
                "    CategoryName TEXT" +
                ");";
        db.execSQL(sqlCreateCategoryTable);


        String sqlCreateTaskTable = "CREATE TABLE Task (" +
                "    TaskId INTEGER PRIMARY KEY," +
                "    Title TEXT," +
                "    Description TEXT," +
                "    DueDate TEXT," +
                "    Status INTEGER," +
                "    CategoryId INTEGER," +
                "    FOREIGN KEY (CategoryId) REFERENCES category(CategoryId)" +
                ");";
        db.execSQL(sqlCreateTaskTable);






    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
    }

    public List<Task> getByDateToday(String date) {
        List<Task> listtasks = new ArrayList<>();

        // Chuẩn bị câu truy vấn
        String whereClause = "SUBSTR(DueDate, 1, 10) like ?";
        String[] whereArgs = {date};

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(
                "Task",         // Tên bảng
                null,           // Các cột cần lấy
                whereClause,    // Điều kiện WHERE
                whereArgs,      // Giá trị thay thế cho điều kiện WHERE
                null,           // GROUP BY
                null,           // HAVING
                null            // ORDER BY
        );

        // Duyệt qua kết quả truy vấn và thêm vào danh sách
        while (cursor != null && cursor.moveToNext()) {
            int taskid = cursor.getInt(0);
            String title = cursor.getString(1);
            String description = cursor.getString(2);
            String dueDateStr = cursor.getString(3);

            SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss", Locale.US);
            Date dueDate = null;
            try {
                dueDate = formatter.parse(dueDateStr);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            int status = cursor.getInt(4);
            int categoryId = cursor.getInt(5);

            // Tạo đối tượng Task và thêm vào danh sách
            Task task = new Task(taskid, title, description, dueDate, status, categoryId);
            listtasks.add(task);
        }

        // Đóng Cursor sau khi sử dụng
        cursor.close();

        return listtasks;
    }

    public List<Task> getOverDue(){
        List<Task> listtasks = new ArrayList<>();
        SimpleDateFormat dayFormat = new SimpleDateFormat("MM-dd-yyyy", Locale.US);
        String todayDateStr = dayFormat.format(new Date());
        // Chuẩn bị câu truy vấn
        String whereClause = "SUBSTR(DueDate, 1, 10) < ?";
        String[] whereArgs = {todayDateStr};

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(
                "Task",         // Tên bảng
                null,           // Các cột cần lấy
                whereClause,    // Điều kiện WHERE
                whereArgs,      // Giá trị thay thế cho điều kiện WHERE
                null,           // GROUP BY
                null,           // HAVING
                null            // ORDER BY
        );

        // Duyệt qua kết quả truy vấn và thêm vào danh sách
        while (cursor != null && cursor.moveToNext()) {
            int taskid = cursor.getInt(0);
            String title = cursor.getString(1);
            String description = cursor.getString(2);
            String dueDateStr = cursor.getString(3);

            SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss", Locale.US);
            Date dueDate = null;
            try {
                dueDate = formatter.parse(dueDateStr);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            int status = cursor.getInt(4);
            int categoryId = cursor.getInt(5);

            // Tạo đối tượng Task và thêm vào danh sách
            Task task = new Task(taskid, title, description, dueDate, status, categoryId);
            listtasks.add(task);
        }

        // Đóng Cursor sau khi sử dụng
        cursor.close();

        return listtasks;
    }

    public List<Task> getAllTasks() {
        List<Task> taskList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(
                "Task",    // Tên bảng
                null,      // Lấy tất cả các cột
                null,      // Không có điều kiện WHERE
                null,      // Không có giá trị thay thế cho điều kiện WHERE
                null,      // Không GROUP BY
                null,      // Không có điều kiện HAVING
                null       // Không có điều kiện ORDER BY
        );

        if (cursor != null && cursor.moveToFirst()) {
            do {
                int taskid = cursor.getInt(0);
                String title = cursor.getString(1);
                String description = cursor.getString(2);
                String dueDateStr = cursor.getString(3);
                int status = cursor.getInt(4);
                int categoryId = cursor.getInt(5);
                Log.d("getAllTasks", "DueDateStr: " + dueDateStr);

                SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss", Locale.US);
                Date dueDate = null;
                try {
                    dueDate = formatter.parse(dueDateStr);
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                // Kiểm tra và in giá trị dueDate
                if (dueDate == null) {
                    Log.e("getAllTasks", "Failed to parse dueDate: " + dueDateStr);
                } else {
                    Log.d("getAllTasks", "Parsed dueDate: " + dueDate.toString());
                }

                Task task = new Task(taskid, title, description, dueDate, status, categoryId);
                taskList.add(task);
            } while (cursor.moveToNext());

            cursor.close();
        }

        return taskList;
    }
    public void addTask(Task task) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("TaskId", task.getUserId());
        values.put("Title", task.getTitle());
        values.put("Description", task.getDescription());
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        String dueDateString = sdf.format(task.getDueDate());
        values.put("DueDate", dueDateString);
        values.put("Status", task.getStatus());
        values.put("CategoryId", task.getCategoryId());

        db.insert("Task", null, values);
        db.close();
    }
    public void addCategory(Category category) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("CategoryId", category.getCategoryId());
        values.put("CategoryName", category.getName());

        // Thực hiện lệnh chèn vào bảng
        long rowId = db.insert("category", null, values);

        // Kiểm tra xem thêm thành công hay không
        if (rowId != -1) {
            Log.d("SQLiteHelper", "Inserted new category with ID: " + rowId);
        } else {
            Log.e("SQLiteHelper", "Failed to insert new category");
        }

        // Đóng kết nối đến cơ sở dữ liệu
        db.close();
    }
    private boolean checkTableExists(String tableName, SQLiteDatabase db) {
        Cursor cursor = db.rawQuery("SELECT name FROM sqlite_master WHERE type='table' AND name=?", new String[]{tableName});
        boolean exists = (cursor.getCount() > 0);
        cursor.close();
        return exists;
    }
    public void deleteAllTasks() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("Task", null, null);
        db.close();
    }

}
