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

export default function DaiilyTable ({dailyExp = [],}) {
    return (
        <div className = "allTables-table-wrapper">
            <table className = "allTables-table">
                <thead>
                    <tr>
                        <th>Date</th>
                        <th>Food</th>
                        <th>Groceries</th>
                        <th>Haircut</th>
                        <th>Household</th>
                        <th>Medical</th>
                        <th>Parents</th>
                        <th>Transport</th>
                        <th>Eating Out</th>
                        <th>Holiday</th>
                        <th>Recreational</th>
                        <th>Shopping</th>
                        <th>Sports</th>
                        <th>Tech</th>
                    </tr>
                </thead>

                <tbody>
                    {(dailyExp || [])
                    .map((daily) => (
                        <tr key = {daily.date}>
                            <td>{cleanText(daily.date)}</td>
                            <td>{formatMoney(daily.food)}</td>
                            <td>{formatMoney(daily.groceries)}</td>
                            <td>{formatMoney(daily.haircut)}</td>
                            <td>{formatMoney(daily.household)}</td>
                            <td>{formatMoney(daily.medical)}</td>
                            <td>{formatMoney(daily.parents)}</td>
                            <td>{formatMoney(daily.transport)}</td>
                            <td>{formatMoney(daily.eating_out)}</td>
                            <td>{formatMoney(daily.holiday)}</td>
                            <td>{formatMoney(daily.recreational)}</td>
                            <td>{formatMoney(daily.shopping)}</td>
                            <td>{formatMoney(daily.sports)}</td>
                            <td>{formatMoney(daily.tech)}</td>
                        </tr>
                    ))}
                </tbody>
            </table>
        </div>
    );
}