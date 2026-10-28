import "../styles/Textfield.css";

export default function Textfield 
({name, label, type = "text", value,
onChange, disabled, className = "",}) {
    return ( 
    <div className = {className}> 
        <label htmlFor = {name}>
            {label}
        </label>

            <div className = 
            {type === "number" 
            ? "currency-input" : ""}>
                {type === "number" && (
                    <span className = 
                    "currency-symbol">
                        $
                    </span>
                )}

                <input
                    id = {name}
                    name = {name}
                    type = {type}
                    value = {value}
                    onChange = {onChange}
                    disabled = {disabled}/>
            </div>
        </div>
    );
}