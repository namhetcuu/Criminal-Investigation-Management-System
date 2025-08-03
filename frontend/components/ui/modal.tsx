"use client"

import type React from "react"
import { Button } from "@/components/ui/button"

interface ModalProps {
  isOpen: boolean
  onClose: () => void
  title: string
  description?: string
  children: React.ReactNode
  onCancel?: () => void
  onSubmit?: () => void
  submitLabel?: string
}

export function Modal({
  isOpen,
  onClose,
  title,
  description,
  children,
  onCancel,
  onSubmit,
  submitLabel = "Create",
}: ModalProps) {
  if (!isOpen) return null

  return (
    <div className="fixed inset-0 bg-black bg-opacity-50 flex items-center justify-center z-50 p-4">
      <div className="bg-white rounded-2xl shadow-2xl max-w-2xl w-full max-h-[90vh] overflow-y-auto">
        <div className="p-8">
          {/* Header */}
          <div className="text-center mb-6">
            <h2 className="text-2xl font-bold text-gray-900 mb-2">{title}</h2>
            {description && <p className="text-sm text-gray-600">{description}</p>}
          </div>

          {/* Content */}
          <div className="mb-8">{children}</div>

          {/* Footer */}
          <div className="flex justify-end space-x-4">
            <Button
              onClick={onCancel || onClose}
              variant="outline"
              className="px-8 py-2 bg-gray-200 hover:bg-gray-300 text-gray-700"
            >
              Cancel
            </Button>
            <Button onClick={onSubmit} className="px-8 py-2 bg-gray-800 hover:bg-gray-900 text-white">
              {submitLabel}
            </Button>
          </div>
        </div>
      </div>
    </div>
  )
}
