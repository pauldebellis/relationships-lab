package generalassembly.yuliyakaleda.relationships_lab;

import android.database.Cursor;
import android.database.MatrixCursor;
import android.database.MergeCursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.Toast;


public class DialogActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText firstNameInput;
    private EditText lastNameInput;
    private EditText salaryInput;
    private Spinner company_spinner;
    private Button addData;
    private Button cancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_layout);

        firstNameInput = (EditText) findViewById(R.id.first_name_input);
        lastNameInput = (EditText) findViewById(R.id.last_name_input);
        salaryInput = (EditText) findViewById(R.id.salary_input);
        company_spinner = (Spinner) findViewById(R.id.select_company);
        addData = (Button) findViewById(R.id.add_button);
        cancel = (Button) findViewById(R.id.cancel_button);

        Cursor cursor = Helper.getInstance(this).getAllCompanies();

        //Add an extra option to the top of the spinner that says "Select a Company".
        //Create a MatrixCursor to help us modify our original Cursor.
        MatrixCursor matrixCursor = new MatrixCursor(new String[]{Helper.CompanyTable._ID, Helper.CompanyTable.COLUMN_NAME});
        //Add a single row to the MatrixCursor.
        matrixCursor.addRow(new Object[]{0, getString(R.string.select_company)});
        //Merge the matrix cursor into the original cursor. Now we have the new default option.
        cursor = new MergeCursor(new Cursor[]{matrixCursor, cursor});

        //Apply out Cursor of company names to the Spinner via a SimpleCursorAdapter.
        SimpleCursorAdapter adapter = new SimpleCursorAdapter(
                this,
                android.R.layout.simple_spinner_item,
                cursor,
                new String[]{Helper.CompanyTable.COLUMN_NAME},
                new int[]{android.R.id.text1},
                0
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        company_spinner.setAdapter(adapter);

        //Setup the button clicks.
        addData.setOnClickListener(this);
        cancel.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.add_button:
                if(addDataToDb()) finish();
                break;
            case R.id.cancel_button:
                finish();
                break;
        }
    }

    /**
     * Adds a row to the Employee database based on the criteria entered by the user.
     */
    private boolean addDataToDb() {
        String firstName = firstNameInput.getText().toString().trim();
        String lastName = lastNameInput.getText().toString().trim();

        //prevent the Employee being saved if the name fields are empty.
        if(firstName.isEmpty()) {
            firstNameInput.setError(getString(R.string.first_name_required));
            return false;
        }
        if(lastName.isEmpty()) {
            lastNameInput.setError(getString(R.string.last_name_required));
            return false;
        }

        //Set the salary to 0 if field is empty.
        String salaryString = salaryInput.getText().toString();
        int salary = salaryString.isEmpty() ? 0 : Integer.parseInt(salaryString);

        //Get the companyID from the Cursor by finding the selected position of the Spinner.
        Cursor cursor = ((SimpleCursorAdapter) company_spinner.getAdapter()).getCursor();
        cursor.moveToPosition(company_spinner.getSelectedItemPosition());
        long companyID = cursor.getLong(cursor.getColumnIndex(Helper.CompanyTable._ID));

        //Instantiate a new Employee.
        Employee employee = new Employee(firstName, lastName, salary, companyID);

        Helper helper = Helper.getInstance(DialogActivity.this);

        //Save the Employee to the database.
        helper.insertEmployee(employee);

        Toast.makeText(DialogActivity.this, R.string.data_added_to_db, Toast.LENGTH_LONG).show();
        return true;
    }
}

