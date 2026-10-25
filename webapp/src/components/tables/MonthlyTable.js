import "../../styles/Table.css";

function cleanText (text) {
    if (!text) 
    return "-";
    const trimmed = 
    text
    .toString()
    .trim();
    return trimmed
    .length === 0 
    ? "-" : trimmed;
}

function formatMoney (value) {
    if (value === null || 
    value === undefined || 
    value === "") return "-";
    return `$${Number(value)
    .toFixed(2)}`;
}

export default function MonthlyTable 
({monthlyExp = [],}) {
    const sortedMonthly = 
    [...monthlyExp]
    .sort((a, b) => {
        const monthA = 
        a.month || "";
        const monthB = 
        b.month || "";
        return monthB
        .localeCompare(monthA);
    });

    return (
        <div className = 
        "allTables-table-wrapper">
            <table className = 
            "allTables-table">
                <thead>
                    <tr>
                        <th>Month</th>
                        <th>Income</th>
                        <th>Debt</th>
                        <th>Emergency</th>
                        <th>Investments</th>
                        <th>Bills</th>
                        <th>Food</th>
                        <th>Groceries</th>
                        <th>Haircut</th>
                        <th>Household</th>
                        <th>Medical</th>
                        <th>Parents</th>
                        <th>Transport</th>
                        <th>Wants</th>
                        <th>Others</th>
                        <th>Savings</th>
                    </tr>
                </thead>

                <tbody>
                    {sortedMonthly
                    .map((monthly) => (
                        <tr key = 
                        {monthly.month}>
                            <td>{cleanText(monthly.month)}</td>
                            <td>{formatMoney(monthly.income)}</td>
                            <td>{formatMoney(monthly.debt)}</td>
                            <td>{formatMoney(monthly.emergency)}</td>
                            <td>{formatMoney(monthly.investments)}</td>
                            <td>{formatMoney(monthly.bills)}</td>
                            <td>{formatMoney(monthly.food)}</td>
                            <td>{formatMoney(monthly.groceries)}</td>
                            <td>{formatMoney(monthly.haircut)}</td>
                            <td>{formatMoney(monthly.household)}</td>
                            <td>{formatMoney(monthly.medical)}</td>
                            <td>{formatMoney(monthly.parents)}</td>
                            <td>{formatMoney(monthly.transport)}</td>
                            <td>{formatMoney(monthly.wants)}</td>
                            <td>{formatMoney(monthly.others)}</td>
                            <td>{formatMoney(monthly.savings)}</td>
                        </tr>
                    ))}
                </tbody>
            </table>
        </div>
    );
}