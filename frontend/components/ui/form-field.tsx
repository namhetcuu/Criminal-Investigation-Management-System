"use client"

import { Label } from "@/components/ui/label"
import { Input } from "@/components/ui/input"

interface FormFieldProps {
  label: string
  id: string
  value: string
  onChange: (value: string) => void
  required?: boolean
  type?: string
  placeholder?: string
}

export function FormField({
  label,
  id,
  value,
  onChange,
  required = false,
  type = "text",
  placeholder,
}: FormFieldProps) {
  return (
    <div>
      <Label htmlFor={id} className="text-sm font-medium">
        {label} {required && <span className="text-red-500">*</span>}
      </Label>
      <Input
        id={id}
        type={type}
        value={value}
        onChange={(e) => onChange(e.target.value)}
        placeholder={placeholder}
        className="mt-1 bg-gray-100"
      />
    </div>
  )
}
