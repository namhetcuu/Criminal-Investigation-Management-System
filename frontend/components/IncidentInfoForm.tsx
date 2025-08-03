import { useState } from "react";
import { Input } from "@/components/ui/input";
import {
    Select,
    SelectTrigger,
    SelectValue,
    SelectContent,
    SelectItem,
} from "@/components/ui/select";
import { Textarea } from "@/components/ui/textarea";
import { Label } from "@/components/ui/label";
import { Button } from "@/components/ui/button";
import {
    Popover,
    PopoverTrigger,
    PopoverContent,
} from "@/components/ui/popover";
import { format } from "date-fns";
import { Calendar } from "@/components/ui/calendar";

interface IncidentInfoFormProps {
    form: {
        typeOfCrime?: string;
        severity?: string;
        datetime?: Date | string;
        address?: string;
        description?: string;
    };
    setForm: React.Dispatch<React.SetStateAction<any>>;
    date: Date | undefined;
    setDate: React.Dispatch<React.SetStateAction<Date | undefined>>;
}

export default function IncidentInfoForm({
    form,
    setForm,
    date,
    setDate,
}: IncidentInfoFormProps) {
    const [open, setOpen] = useState(false);

    const handleChange = (e: React.ChangeEvent<HTMLInputElement | HTMLTextAreaElement>) => {
        setForm({ ...form, [e.target.name]: e.target.value });
    };

    return (
        <div className="my-8">
            <div className="flex items-center mb-8">
                <div className="flex-1 border-t border-gray-300" />
                <h2 className="mx-4 font-semibold text-lg sm:text-2xl">Incident Information</h2>
                <div className="flex-1 border-t border-gray-300" />
            </div>

            <div className="grid grid-cols-1 md:grid-cols-2 gap-x-10 gap-y-6">
                {/* Type of Crime */}
                <div className="space-y-2">
                    <Label htmlFor="typeOfCrime">Type of crime *</Label>
                    <Select
                        onValueChange={(val) => setForm({ ...form, typeOfCrime: val })}
                        value={form.typeOfCrime}
                    >
                        <SelectTrigger className="w-full">
                            <SelectValue placeholder="Select an option" />
                        </SelectTrigger>
                        <SelectContent className="bg-white border border-gray-200 rounded-md shadow-md">
                            <SelectItem value="crimes-against-persons">Crimes Against Persons</SelectItem>
                            <SelectItem value="crimes-against-property">Crimes Against Property</SelectItem>
                            <SelectItem value="white-collar-crimes">White-Collar Crimes</SelectItem>
                            <SelectItem value="cyber-crimes">Cyber Crimes</SelectItem>
                            <SelectItem value="drug-related-crimes">Drug-related Crimes</SelectItem>
                            <SelectItem value="public-order-crimes">Public Order Crimes</SelectItem>
                        </SelectContent>
                    </Select>
                </div>

                {/* Severity */}
                <div className="space-y-2">
                    <Label htmlFor="severity">Severity *</Label>
                    <Select
                        onValueChange={(val) => setForm({ ...form, severity: val })}
                        value={form.severity}
                    >
                        <SelectTrigger className="w-full">
                            <SelectValue placeholder="Select an option" />
                        </SelectTrigger>
                        <SelectContent className="bg-white border border-gray-200 rounded-md shadow-md">
                            <SelectItem value="minor">Minor</SelectItem>
                            <SelectItem value="moderate">Moderate</SelectItem>
                            <SelectItem value="serious">Serious</SelectItem>
                            <SelectItem value="critical">Critical</SelectItem>
                        </SelectContent>
                    </Select>
                </div>

                {/* Datetime */}
                <div className="space-y-2">
                    <Label htmlFor="datetime">Datetime of occurrence *</Label>
                    <Popover open={open} onOpenChange={setOpen}>
                        <PopoverTrigger asChild>
                            <Button
                                variant="outline"
                                className="w-full justify-start text-left font-normal"
                            >
                                {form.datetime
                                    ? format(new Date(form.datetime), "dd/MM/yyyy")
                                    : <span className="text-muted-foreground">Choose</span>}
                            </Button>
                        </PopoverTrigger>
                        <PopoverContent align="start" className="p-0">
                            <Calendar
                                mode="single"
                                selected={date}
                                onSelect={(selectedDate) => {
                                    if (selectedDate) {
                                        setDate(selectedDate);
                                        setForm({ ...form, datetime: selectedDate });
                                        setOpen(false);
                                    }
                                }}
                                initialFocus
                            />
                        </PopoverContent>
                    </Popover>
                </div>

                {/* Address */}
                <div className="space-y-2">
                    <Label htmlFor="address">Detailed address</Label>
                    <Input
                        name="address"
                        className="w-full"
                        onChange={handleChange}
                        value={form.address || ""}
                    />
                </div>
            </div>

            {/* Description */}
            <div className="mt-6 space-y-2">
                <Label htmlFor="description">Description of the incident</Label>
                <Textarea
                    name="description"
                    placeholder="Briefly describe what happened..."
                    className="w-full"
                    onChange={handleChange}
                    value={form.description || ""}
                />
            </div>
        </div>
    );
}
