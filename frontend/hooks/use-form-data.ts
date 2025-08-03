"use client"

import { useState } from "react"
import type { FormData } from "@/types/form"

const initialFormData: FormData = {
  fullName: "",
  email: "",
  phoneNumber: "",
  address: "",
  relationship: "",
  crimeType: "",
  severity: "",
  datetime: "",
  detailedAddress: "",
  description: "",
}

export function useFormData() {
  const [formData, setFormData] = useState<FormData>(initialFormData)

  const updateField = (field: keyof FormData, value: string) => {
    setFormData((prev) => ({ ...prev, [field]: value }))
  }

  const resetForm = () => {
    setFormData(initialFormData)
  }

  return { formData, updateField, resetForm }
}
