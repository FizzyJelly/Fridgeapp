package pl.edu.agh.fridgeapp.view_controllers;

import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import pl.edu.agh.fridgeapp.R;
import pl.edu.agh.fridgeapp.activities.MainActivity;
import pl.edu.agh.fridgeapp.model.Date;
import pl.edu.agh.fridgeapp.model.FridgeItem;
import pl.edu.agh.fridgeapp.model.User;

public class FinanceSummaryLayoutSetter implements ILayoutSetter {

    private MainActivity context;
    private List<FridgeItem> fridgeItems;
    private Date.Months currentMonth;
    private Integer currentYear;

    public FinanceSummaryLayoutSetter(MainActivity context) {
        this.context = context;
        currentMonth = Date.Months.values()[Calendar.getInstance().get(Calendar.MONTH)];
        currentYear = Calendar.getInstance().get(Calendar.YEAR);
    }

    @Override
    public void setLayout() {
        context.setContentView(R.layout.finance_summary_layout);

        fridgeItems = context.getFridge().getItems();
        Iterator<FridgeItem> iterator = fridgeItems.iterator();
        FridgeItem temp;
        while (iterator.hasNext()) {
            temp = iterator.next();
            if (!(temp.getAddedDate().getMonth().equals(currentMonth) && temp.getAddedDate().getYear().equals(currentYear))) {
                iterator.remove();
            }
        }

        TextView summaryMonth = context.findViewById(R.id.summary_month);
        TextView youSpent = context.findViewById(R.id.you_spent_text);
        TextView sharedValue = context.findViewById(R.id.shared_items_value_text);

        String summaryMonthText = currentMonth.getName() + " " + currentYear;
        String youSpentText = youSpent.getText().toString() + getPriceString(calculateSpentByUser());
        String sharedValueText = sharedValue.getText().toString() + getPriceString(calculateSpentOnCommon());

        summaryMonth.setText(summaryMonthText);
        youSpent.setText(youSpentText);
        sharedValue.setText(sharedValueText);

        Map<User, Double> debtOwners = calculateDebts();

        LinearLayout debtsLayout = context.findViewById(R.id.debts_layout);
        if (debtOwners.size() != 0) {

            for (Map.Entry<User, Double> debt : debtOwners.entrySet()) {
                View debtView = context.getLayoutInflater().inflate(R.layout.debt_view, null);
                TextView debtOwner = debtView.findViewById(R.id.debt_owner);
                TextView debtValue = debtView.findViewById(R.id.debt_value);

                debtOwner.setText(debt.getKey().getName());
                debtValue.setText(" " + getPriceString(debt.getValue()));

                debtsLayout.addView(debtView);

            }
        } else {
            View noDebtText = context.getLayoutInflater().inflate(R.layout.no_debt_text_view, null);

            debtsLayout.addView(noDebtText);
        }


        ImageButton previousMonthButton = context.findViewById(R.id.previous_month_button);
        ImageButton nextMonthButton = context.findViewById(R.id.next_month_button);

        previousMonthButton.setOnClickListener(v -> {
            if (currentMonth.getNumber().equals(1))
                currentYear--;
            currentMonth = currentMonth.previousMonth();
            setLayout();

        });
        nextMonthButton.setOnClickListener(v -> {
            if (currentMonth.getNumber().equals(12))
                currentYear++;
            currentMonth = currentMonth.nextMonth();
            setLayout();
        });

    }

    public double calculateSpentByUser() {

        double result = 0;
        User localUser = context.getLocalUser();

        for (FridgeItem item : fridgeItems) {
            if (item.getBuyer().equals(localUser)) {
                result += item.getPrice();
            }
        }
        return result;
    }

    public double calculateSpentOnCommon() {
        double result = 0;

        for (FridgeItem item : fridgeItems) {
            if (item.getOwner().getName().equals("Shared")) {
                result += item.getPrice();
            }
        }
        return result;

    }

    public Map<User, Double> calculateDebts() {

        HashMap<User, Double> result = new HashMap<>();
        Double temp;
        User itemBuyer;
        int userCount = context.getFridge().getOwners().size();

        for (FridgeItem item : fridgeItems) {
            if (item.getOwner().getName().equals("Shared") && !item.getBuyer().equals(context.getLocalUser())) {
                itemBuyer = item.getBuyer();
                if (result.keySet().contains(itemBuyer)) {
                    temp = result.get(itemBuyer);
                    result.remove(itemBuyer);
                } else {
                    temp = 0.0;
                }
                result.put(itemBuyer, temp + item.getPrice() / userCount);
            }
        }
        return result;
    }

    private String getPriceString(Double price) {
        Double tempDouble = price * 100;
        Integer tempInt = tempDouble.intValue();
        Double resultDouble = tempInt / 100.0;

        return resultDouble.toString() + " PLN";
    }
}
