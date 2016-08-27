package generalassembly.yuliyakaleda.relationships_lab;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

import java.util.Arrays;
import java.util.List;

public class Helper extends SQLiteOpenHelper {

    public static final String DB_NAME = "jobs_db";

    public Helper(Context context) {
        super(context, DB_NAME, null, 1);
    }

    private static Helper INSTANCE;

    public static synchronized Helper getInstance(Context context) {
        if (INSTANCE == null)
            INSTANCE = new Helper(context.getApplicationContext());
        return INSTANCE;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES_EMPLOYEES);
        db.execSQL(SQL_CREATE_ENTRIES_COMPANY);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_ENTRIES_EMPLOYEES);
        db.execSQL(SQL_DELETE_ENTRIES_COMPANY);
        onCreate(db);
    }

    /**
     * Inner class which represents the columns in our employee table.
     */
    public static abstract class EmployeeTable implements BaseColumns {
        public static final String TABLE_NAME = "employee";
        public static final String COLUMN_FIRST_NAME = "first_name";
        public static final String COLUMN_LAST_NAME = "last_name";
        public static final String COLUMN_SALARY = "salary";
        public static final String COLUMN_COMPANY_ID = "company_id";
    }

    /**
     * Inner class which represents the columns in our company table.
     */
    public static abstract class CompanyTable implements BaseColumns {
        public static final String TABLE_NAME = "company";
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_CITY = "city";
        public static final String COLUMN_STATE = "state";
    }

    /**
     * SQL command to create our employee table.
     */
    private static final String SQL_CREATE_ENTRIES_EMPLOYEES = "CREATE TABLE " +
            EmployeeTable.TABLE_NAME + " (" +
            EmployeeTable._ID + " INTEGER PRIMARY KEY," +
            EmployeeTable.COLUMN_FIRST_NAME + " TEXT," +
            EmployeeTable.COLUMN_LAST_NAME + " TEXT," +
            EmployeeTable.COLUMN_SALARY + " INTEGER," +
            EmployeeTable.COLUMN_COMPANY_ID + " LONG" + ")";

    /**
     * SQL command to delete our employee table.
     */
    private static final String SQL_DELETE_ENTRIES_EMPLOYEES = "DROP TABLE IF EXISTS " +
            EmployeeTable.TABLE_NAME;

    /**
     * SQL command to create our company table.
     */
    private static final String SQL_CREATE_ENTRIES_COMPANY = "CREATE TABLE " +
            CompanyTable.TABLE_NAME + " (" +
            CompanyTable._ID + " INTEGER PRIMARY KEY," +
            CompanyTable.COLUMN_NAME + " TEXT," +
            CompanyTable.COLUMN_CITY + " TEXT," +
            CompanyTable.COLUMN_STATE + " TEXT" + ")";

    /**
     * SQL command to delete our company table.
     */
    private static final String SQL_DELETE_ENTRIES_COMPANY = "DROP TABLE IF EXISTS " +
            CompanyTable.TABLE_NAME;

    /**
     * Insert an employee into the datebase.
     * @param employee
     */
    public void insertEmployee(Employee employee) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(EmployeeTable.COLUMN_FIRST_NAME, employee.getFirstName());
        values.put(EmployeeTable.COLUMN_LAST_NAME, employee.getLastName());
        values.put(EmployeeTable.COLUMN_SALARY, employee.getSalary());
        values.put(EmployeeTable.COLUMN_COMPANY_ID, employee.getCompanyID());
        db.insertOrThrow(EmployeeTable.TABLE_NAME, null, values);
    }

    /**
     * Insert a company into the database.
     * @param company
     */
    public void insertCompany(Company company) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(CompanyTable.COLUMN_NAME, company.getName());
        values.put(CompanyTable.COLUMN_CITY, company.getCity());
        values.put(CompanyTable.COLUMN_STATE, company.getState());
        db.insertOrThrow(CompanyTable.TABLE_NAME, null, values);
    }

    /**
     * Add some default data to the database.
     */
    public void addDataToDb() {

        insertCompany(new Company("Google", "Mountain View", "California"));
        insertCompany(new Company("GA", "Santa Monica", "California"));
        insertCompany(new Company("McDonalds", "Boston", "Massachusetts"));
        insertCompany(new Company("Macys", "New York", "New York"));

        insertEmployee(new Employee("John", "Smith", 110_000, 1));
        insertEmployee(new Employee("David", "McWill", 40_000, 2));
        insertEmployee(new Employee("Katerina", "Wise", 55_000, 3));
        insertEmployee(new Employee("Donald", "Lee", 70_000, 4));
        insertEmployee(new Employee("Gary", "Henwood", 65_000, 4));
        insertEmployee(new Employee("Anthony", "Bright", 30_000, 3));
        insertEmployee(new Employee("William", "Newey", 25_000, 4));
        insertEmployee(new Employee("Melony", "Smith", 123_000, 2));
        insertEmployee(new Employee("Jobless", "Joe"));
    }

    /**
     * TODO: Get the names of all the employees who work a Macys.
     * @return List of employee names.
     */
    public List<String> getEmployeesWorkingAtMacys() {
        List<String> result = Arrays.asList();
        SQLiteDatabase db = getReadableDatabase();
        String query = "SELECT "+EmployeeTable.COLUMN_FIRST_NAME+", "+EmployeeTable.COLUMN_LAST_NAME+" FROM "+EmployeeTable.TABLE_NAME+" INNER JOIN "+EmployeeTable.TABLE_NAME+" ON "+EmployeeTable.COLUMN_COMPANY_ID+" = "+CompanyTable._ID;
        Cursor cursor = db.rawQuery(query, null);
        while(cursor.moveToNext()){
            result = Arrays.asList( cursor.getString(cursor.getColumnIndex(EmployeeTable.COLUMN_FIRST_NAME)) +
                    cursor.getString(cursor.getColumnIndex(EmployeeTable.COLUMN_LAST_NAME)));
        }
        cursor.close();
        return result;
    }

    /**
     * TODO: Get the Names of all the employees who work in companies based in California.
     * @return List of employee names.
     */
    public List<String> getEmployeesLocatedInCalifornia() {
        return null;
    }

    /**
     * TODO: Get the names of all companies who have employees earning 6 figure salaries.
     * @return List of company names.
     */
    public List<String> getCompanysWithSixFigureSalary() {
        return null;
    }

    /**
     * TODO: Get the names of everyone who doesn't have a job (Their company_id doesn't match any companies in the database).
     * @return List of names.
     */
    public List<String> getUnemployed() {
        return null;
    }

    /**
     * TODO: Get the names off all companies in the database.
     * @return Cursor
     */
    public Cursor getAllCompanies(){
        return null;
    }
}
