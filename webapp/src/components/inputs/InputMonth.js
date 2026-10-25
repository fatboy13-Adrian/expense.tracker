export default function SelectMonth({
name,
value,
onChange,
disabled
}) {
    //Get current date in SG time
    const singaporeDate = new Date()
    .toLocaleDateString("en-CA", {
        timeZone: "Asia/Singapore",
        year: "numeric",
        month: "2-digit"
    });

    //Ensure value follows YYYY-MM format
    const [year, month] = 
    singaporeDate
    .split("-");
    const maxMonth = 
    `${year}-${month}`;

    return (
        <div className = 
        "input-month-container">
            <label htmlFor = 
            "input-month">
                Month
            </label>

            <input
                id = 
                "input-month"
                className = 
                "month-input"
                type = "month"
                name = {name}
                value = {value || ""}
                onChange = {onChange}
                disabled = {disabled}
                max = {maxMonth}/>
        </div>
    );
}