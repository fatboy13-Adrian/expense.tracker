import "../../styles/Table.css";

function cleanText (text) {
    if (text === null || 
    text === undefined) {
        return "-";
    }

    const trimmed = 
    text
    .toString()
    .trim();
    return trimmed
    .length === 0 
    ? "-" 
    : trimmed;
}

function formatMoney (value) {
    if (value === null ||
    value === undefined ||
    value === "") {
        return "-";
    }

    const amount = 
    Number(value);

    //Handle invalid numeric values
    if (!Number
    .isFinite(amount)) {
        return "-";
    }

    return `$${amount.toFixed(2)}`;
}

export default function YearlyTable 
({yearlyExp = []}) {
    return (
        <div className = 
        "allTables-table-wrapper">
            <table className = 
            "allTables-table">
                <thead>
                    <tr>
                        <th>Year</th>
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
                    {yearlyExp.map
                    ((yearly, index) => (
                        <tr key={yearly.year ?? index}>
                            <td>{cleanText(yearly.year)}</td>
                            <td>{formatMoney(yearly.income)}</td>
                            <td>{formatMoney(yearly.debt)}</td>
                            <td>{formatMoney(yearly.emergency)}</td>
                            <td>{formatMoney(yearly.investments)}</td>
                            <td>{formatMoney(yearly.bills)}</td>
                            <td>{formatMoney(yearly.food)}</td>
                            <td>{formatMoney(yearly.groceries)}</td>
                            <td>{formatMoney(yearly.haircut)}</td>
                            <td>{formatMoney(yearly.household)}</td>
                            <td>{formatMoney(yearly.medical)}</td>
                            <td>{formatMoney(yearly.parents)}</td>
                            <td>{formatMoney(yearly.transport)}</td>
                            <td>{formatMoney(yearly.wants)}</td>
                            <td>{formatMoney(yearly.others)}</td>
                            <td>{formatMoney(yearly.savings)}</td>
                        </tr>
                    ))}
                </tbody>
            </table>
        </div>
    );
}