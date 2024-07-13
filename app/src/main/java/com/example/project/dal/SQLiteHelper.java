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
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class SQLiteHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "Project.sqlite";
    private static int DATABASE_VERSION = 13;
    public SQLiteHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(@NonNull SQLiteDatabase db) {
        String sqlCreateCategoryTable = "CREATE TABLE category (" +
                "    CategoryId INTEGER PRIMARY KEY AUTOINCREMENT," +
                "    CategoryName TEXT" +
                ");";
        db.execSQL(sqlCreateCategoryTable);


        String sqlCreateTaskTable = "CREATE TABLE Task (" +
                "    TaskId INTEGER PRIMARY KEY AUTOINCREMENT," +
                "    Title TEXT," +
                "    Description TEXT," +
                "    DueDate TEXT," +
                "    Status INTEGER," +
                "    IsLocal INTEGER, " +
                "    CategoryId INTEGER," +
                "    FOREIGN KEY (CategoryId) REFERENCES category(CategoryId)" +
                ");";
        db.execSQL(sqlCreateTaskTable);

        String createLocalChangeTable = "CREATE TABLE LocalChanges (" +
                "    TaskId INTEGER," +
                "    Operation INTEGER" +
                ");";
        db.execSQL(createLocalChangeTable);

        seedTasks(db);



    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

    private void seedTasks(SQLiteDatabase db){
        ContentValues values1 = new ContentValues();
        values1.put("CategoryId", 0); // Giá trị của CategoryId
        values1.put("CategoryName", "Work"); // Giá trị của CategoryName
        db.insert("category", null, values1);

        ContentValues values2 = new ContentValues();
        values2.put("CategoryId", 1); // Giá trị của CategoryId
        values2.put("CategoryName", "Study"); // Giá trị của CategoryName
        db.insert("category", null, values2);

        ContentValues values3 = new ContentValues();
        values3.put("CategoryId", 2); // Giá trị của CategoryId
        values3.put("CategoryName", "Personal"); // Giá trị của CategoryName
        db.insert("category", null, values3);

        ContentValues values4 = new ContentValues();
        values4.put("CategoryId", 3); // Giá trị của CategoryId
        values4.put("CategoryName", "Do Exercise"); // Giá trị của CategoryName
        db.insert("category", null, values4);

        ContentValues values5 = new ContentValues();
        values5.put("CategoryId", 4); // Giá trị của CategoryId
        values5.put("CategoryName", "Must Do"); // Giá trị của CategoryName
        db.insert("category", null, values5);

        ContentValues taskValues = new ContentValues();
        taskValues.put("TaskId", 0); // Giá trị của UserId
        taskValues.put("Title", "Finish project"); // Giá trị của Title
        taskValues.put("Description", "Complete all tasks by the deadline."); // Giá trị của Description
        taskValues.put("DueDate", "07-10-2024 10:54:12"); // Giá trị của DueDate (vd: "dd-MM-yyy HH:mm:ss")
        taskValues.put("Status", 0); // Giá trị của Status
        taskValues.put("CategoryId", 1); // Giá trị của CategoryId (tham chiếu đến bảng category)
        db.insert("Task", null, taskValues);

        ContentValues taskValues1 = new ContentValues();
        taskValues1.put("TaskId", 1); // Giá trị của UserId
        taskValues1.put("Title", "Finish project"); // Giá trị của Title
        taskValues1.put("Description", "Complete all tasks by the deadline."); // Giá trị của Description
        taskValues1.put("DueDate", "07-10-2024 10:54:12"); // Giá trị của DueDate (vd: "dd-MM-yyy HH:mm:ss")
        taskValues1.put("Status", 0); // Giá trị của Status
        taskValues1.put("CategoryId", 1); // Giá trị của CategoryId (tham chiếu đến bảng category)
        db.insert("Task", null, taskValues1);

        ContentValues taskValues2 = new ContentValues();
        taskValues2.put("TaskId", 2);
        taskValues2.put("Title", "Prepare presentation");
        taskValues2.put("Description", "Create slides for the upcoming meeting.");
        taskValues2.put("DueDate", "07-10-2024 09:00:00");
        taskValues2.put("Status", 0);
        taskValues2.put("CategoryId", 2);
        db.insert("Task", null, taskValues2);

        ContentValues taskValues3 = new ContentValues();
        taskValues3.put("TaskId", 3);
        taskValues3.put("Title", "Review budget");
        taskValues3.put("Description", "Go over the budget report and identify discrepancies.");
        taskValues3.put("DueDate", "07-10-2024 16:30:00");
        taskValues3.put("Status", 1);
        taskValues3.put("CategoryId", 3);
        db.insert("Task", null, taskValues3);

        ContentValues taskValues4 = new ContentValues();
        taskValues4.put("TaskId", 4);
        taskValues4.put("Title", "Team meeting");
        taskValues4.put("Description", "Discuss project progress and next steps with the team.");
        taskValues4.put("DueDate", "07-11-2024 14:00:00");
        taskValues4.put("Status", 0);
        taskValues4.put("CategoryId", 1);
        db.insert("Task", null, taskValues4);

        ContentValues taskValues5 = new ContentValues();
        taskValues5.put("TaskId", 5);
        taskValues5.put("Title", "Write report");
        taskValues5.put("Description", "Draft the final report for the completed project.");
        taskValues5.put("DueDate", "07-13-2024 18:00:00");
        taskValues5.put("Status", 0);
        taskValues5.put("CategoryId", 4);
        db.insert("Task", null, taskValues5);

        ContentValues taskValues6 = new ContentValues();
        taskValues6.put("TaskId", 6);
        taskValues6.put("Title", "Code review");
        taskValues6.put("Description", "Review code submissions and provide feedback.");
        taskValues6.put("DueDate", "07-13-2024 12:00:00");
        taskValues6.put("Status", 1);
        taskValues6.put("CategoryId", 2);
        db.insert("Task", null, taskValues6);
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
            SimpleDateFormat formatter = new SimpleDateFormat("MM-dd-yyyy HH:mm:ss", Locale.US);
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
            Log.d("Task", "ID: " + taskid + ", Title: " + title + ", Description: " + description +
                    ", DueDate: " + dueDateStr + ", Status: " + status + ", CategoryID: " + categoryId);
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

            SimpleDateFormat formatter = new SimpleDateFormat("MM-dd-yyyy HH:mm:ss", Locale.US);
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

    public List<Task> getUpcomingTask() {
        List<Task> listtasks = new ArrayList<>();
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy", Locale.US);
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_YEAR, 1); // Add one day to get tomorrow's date
        String tomorrowDateStr = dateFormat.format(calendar.getTime());

        // Chuẩn bị câu truy vấn
        String whereClause = "SUBSTR(DueDate, 1, 10) = ?";
        String[] whereArgs = {tomorrowDateStr};

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

            SimpleDateFormat formatter = new SimpleDateFormat("MM-dd-yyyy HH:mm:ss", Locale.US);
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
        if (cursor != null) {
            cursor.close();
        }

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
                SimpleDateFormat formatter = new SimpleDateFormat("MM-dd-yyyy HH:mm:ss", Locale.US);
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

    public long addTask(Task task) {

        ContentValues values = new ContentValues();
        values.put("TaskId", task.getUserId());
        values.put("Title", task.getTitle());
        values.put("Description", task.getDescription());
        SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy HH:mm:ss");
        String dueDateString = sdf.format(task.getDueDate());
        values.put("DueDate", dueDateString);
        values.put("Status", task.getStatus());
        values.put("CategoryId", task.getCategoryId());
        SQLiteDatabase db = this.getWritableDatabase();
        return db.insert("Task", null, values);

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

    public List<Category> getAllCategory() {
        List<Category> categoryList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;

            cursor = db.query(
                    "category",    // Tên bảng
                    null,      // Lấy tất cả các cột
                    null,      // Không có điều kiện WHERE
                    null,      // Không có giá trị thay thế cho điều kiện WHERE
                    null,      // Không GROUP BY
                    null,      // Không có điều kiện HAVING
                    null       // Không có điều kiện ORDER BY
            );

            if (cursor != null && cursor.moveToFirst()) {
                do {
                    int id = cursor.getInt(cursor.getColumnIndexOrThrow("CategoryId"));
                    String name = cursor.getString(cursor.getColumnIndexOrThrow("CategoryName"));
                    Category category = new Category(id, name);
                    categoryList.add(category);
                    Log.d("Category", "ID: " + id + ", Name: " + name);
                } while (cursor.moveToNext());
            }

            if (cursor != null) {
                cursor.close();
            }
        return categoryList;
    }

    public Category getCategoryById(int categoryId) {
        Category category = null;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;

        try {
            String[] projection = {"CategoryId", "CategoryName"};
            String selection = "CategoryId = ?";
            String[] selectionArgs = {String.valueOf(categoryId)};

            cursor = db.query(
                    "category",    // Tên bảng
                    projection,    // Các cột cần lấy
                    selection,     // Điều kiện WHERE
                    selectionArgs, // Giá trị thay thế cho điều kiện WHERE
                    null,          // GROUP BY
                    null,          // HAVING
                    null           // ORDER BY
            );

            if (cursor != null && cursor.moveToFirst()) {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow("CategoryId"));
                String name = cursor.getString(cursor.getColumnIndexOrThrow("CategoryName"));
                category = new Category(id, name);
            }
        } catch (Exception e) {
            Log.e("SQLiteHelper", "Error retrieving category by id: " + e.getMessage());
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }

        return category;
    }

    public void updateTask(Task task) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("Title", task.getTitle());
        values.put("Description", task.getDescription());

        SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy HH:mm:ss", Locale.US);
        String dueDateString = sdf.format(task.getDueDate());
        values.put("DueDate", dueDateString);

        values.put("Status", task.getStatus());
        values.put("CategoryId", task.getCategoryId());

        String whereClause = "TaskId = ?";
        String[] whereArgs = {String.valueOf(task.getTaskId())};

        int rowsAffected = db.update("Task", values, whereClause, whereArgs);
        if (rowsAffected > 0) {
            Log.d("SQLiteHelper", "Updated Task with TaskId: " + task.getTaskId());
        } else {
            Log.e("SQLiteHelper", "Failed to update Task with TaskId: " + task.getTaskId());
        }

        db.close();
    }

}
