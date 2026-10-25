export default function InputDate 
({name, 
label = "Date", 
value,
onChange, 
disabled}) {
    const sgTime = 
    new Date()
    .toLocaleDateString("en-CA", {
        timeZone: "Asia/Singapore"
    });

    return (
        <div className = 
        "date-input-container">
            <label htmlFor = {name} 
            className = "date-input-label">
                {label}
            </label>

            <input
                id = {name}
                type = "date"
                name = {name}
                value = {value}
                onChange = {onChange}
                disabled = {disabled}
                max = {sgTime}
                className = "form-control date-input"
                required
            />
        </div>
    );
}