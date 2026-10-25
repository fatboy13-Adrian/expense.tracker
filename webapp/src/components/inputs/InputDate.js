export default function InputDate 
({name, label = "Date", type = "date",
value = "", onChange, disabled = false,
max, min, className = "input"}) {
    return (
        <div className = "input-date">
            <label
                htmlFor = {name}
                className = 
                "input-date-label">
                {label}
            </label>

            <input
                type = {type}
                id = {name}
                name = {name}
                value = {value}
                onChange = {onChange}
                disabled = {disabled}
                max = {max}
                min = {min}
                className = {className}/>
        </div>
    );
}