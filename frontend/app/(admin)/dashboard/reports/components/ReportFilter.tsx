'use client';

import { ChevronDown } from 'lucide-react';

type Props = {
    filters: {
        status: string;
        crimeType: string;
        severity: string;
    };
    onChange: (filters: Props['filters']) => void;
};

const ReportFilter = ({ filters, onChange }: Props) => {
    const handleChange = (key: keyof Props['filters'], value: string) => {
        onChange({ ...filters, [key]: value });
    };

    return (
        <div className="bg-[#2C3E50] flex flex-wrap items-center gap-2 px-4 py-3 rounded-md text-white text-sm">
            {/* All - giả lập filter tổng */}
            <Dropdown label="All" />

            {/* Status Filter */}
            <Dropdown
                label="Status"
                options={['All', 'Approved', 'Pending', 'Rejected']}
                value={filters.status}
                onChange={(val) => handleChange('status', val)}
            />

            {/* Crime Type Filter */}
            <Dropdown
                label="Crime Type"
                options={['All', 'White-Collar Crimes', 'Crimes Against Property']}
                value={filters.crimeType}
                onChange={(val) => handleChange('crimeType', val)}
            />

            {/* Severity Filter */}
            <Dropdown
                label="Severity"
                options={['All', 'Urgent', 'Not urgent']}
                value={filters.severity}
                onChange={(val) => handleChange('severity', val)}
            />

            {/* Created at - không xử lý, chỉ hiển thị */}
            <Dropdown label="Created at" />
        </div>
    );
};

const Dropdown = ({
    label,
    value,
    options,
    onChange,
}: {
    label: string;
    value?: string;
    options?: string[];
    onChange?: (val: string) => void;
}) => {
    const current = value ?? label;

    return (
        <div className="relative">
            <select
                value={current}
                onChange={(e) => onChange?.(e.target.value)}
                className="appearance-none bg-[#3C4E65] text-white px-4 py-2 pr-8 rounded-md text-sm cursor-pointer"
            >
                {options ? (
                    options.map((opt) => (
                        <option key={opt} value={opt}>
                            {opt}
                        </option>
                    ))
                ) : (
                    <option>{label}</option>
                )}
            </select>
            <ChevronDown className="absolute right-2 top-1/2 -translate-y-1/2 text-white w-4 h-4 pointer-events-none" />
        </div>
    );
};

export default ReportFilter;