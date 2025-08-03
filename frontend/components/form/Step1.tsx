'use client';

import { useState } from 'react';
import { Input } from '@/components/ui/input';
import { Label } from '@/components/ui/label';
import { RadioGroup, RadioGroupItem } from '@/components/ui/radio-group';
import { Button } from '@/components/ui/button';
import { Separator } from '@radix-ui/react-dropdown-menu';

type FormData = {
    fullName: string;
    email: string;
    phone: string;
    address: string;
    relationship: string;
};

type Props = {
    data: FormData;
    onNext: (data: FormData) => void;
};

export default function Step1({ data, onNext }: Props) {
    const [localData, setLocalData] = useState<FormData>(data);
    const [errors, setErrors] = useState<Partial<Record<keyof FormData, string>>>({});

    const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
        const { id, value } = e.target;
        setLocalData((prev) => ({ ...prev, [id]: value }));
        setErrors((prev) => ({ ...prev, [id]: '' })); // clear error on change
    };

    const handleRelationshipChange = (value: string) => {
        setLocalData((prev) => ({ ...prev, relationship: value }));
        setErrors((prev) => ({ ...prev, relationship: '' }));
    };

    const validate = () => {
        const newErrors: Partial<Record<keyof FormData, string>> = {};
        if (!localData.fullName.trim()) newErrors.fullName = 'this field is required.';
        if (!localData.email.trim()) newErrors.email = 'this field is required.';
        if (!localData.phone.trim()) newErrors.phone = 'this field is required.';
        if (!localData.relationship.trim()) newErrors.relationship = 'this field is required.';

        setErrors(newErrors);
        return Object.keys(newErrors).length === 0;
    };

    const handleNextClick = () => {
        if (validate()) {
            onNext(localData);
        }
    };

    const relationshipOptions = [
        { value: 'witness', label: 'Witness' },
        { value: 'victim', label: 'Victim' },
        { value: 'suspect', label: 'Suspect' },
    ];

    return (
        <div className="w-full max-w-screen-md mx-auto">
            {/* Title */}
            <div className="flex items-center mb-8">
                <Separator className="flex-1" />
                <h2 className="mx-4 font-semibold text-lg sm:text-2xl">Reporter Information</h2>
                <Separator className="flex-1" />
            </div>

            {/* Form fields */}
            <div className="grid grid-cols-1 md:grid-cols-2 gap-x-10 gap-y-6">
                <div className="space-y-2">
                    <Label htmlFor="fullName">Full name <span className="text-red-500">*</span></Label>
                    <Input id="fullName" value={localData.fullName} onChange={handleChange} placeholder='E.g., John Michael Doe' />
                    {errors.fullName && <p className="text-red-500 text-sm">{errors.fullName}</p>}
                </div>
                <div className="space-y-2">
                    <Label htmlFor="email">Email <span className="text-red-500">*</span></Label>
                    <Input id="email" type="email" value={localData.email} onChange={handleChange} placeholder='E.g., john.doe@example.com' />
                    {errors.email && <p className="text-red-500 text-sm">{errors.email}</p>}
                </div>
                <div className="space-y-2">
                    <Label htmlFor="phone">Phone number <span className="text-red-500">*</span></Label>
                    <Input id="phone" value={localData.phone} onChange={handleChange} placeholder='E.g., +1 (555) 123-4567' />
                    {errors.phone && <p className="text-red-500 text-sm">{errors.phone}</p>}
                </div>
                <div className="space-y-2">
                    <Label htmlFor="address">Address</Label>
                    <Input id="address" value={localData.address} onChange={handleChange} placeholder='E.g., 123 Main St, Anytown, USA' />
                </div>
            </div>

            {/* Relationship */}
            <div className="mt-8 space-y-4">
                <Label className="text-base sm:text-xl">
                    Relationship to the incident <span className="text-red-500">*</span>
                </Label>
                <RadioGroup value={localData.relationship} onValueChange={handleRelationshipChange} className="space-y-4 mt-4">
                    {relationshipOptions.map((option) => (
                        <div key={option.value} className="flex items-center space-x-4">
                            <RadioGroupItem
                                value={option.value}
                                id={option.value}
                                className="w-5 h-5 border-2 border-gray-500"
                            />
                            <Label htmlFor={option.value} className="text-base sm:text-lg">
                                {option.label}
                            </Label>
                        </div>
                    ))}
                </RadioGroup>
                {errors.relationship && <p className="text-red-500 text-sm">{errors.relationship}</p>}
            </div>

            {/* Next Button */}
            <div className="flex justify-end mt-12">
                <Button
                    className="w-32 bg-black text-white font-semibold rounded-lg"
                    onClick={handleNextClick}
                >
                    Next
                </Button>
            </div>
        </div>
    );
}