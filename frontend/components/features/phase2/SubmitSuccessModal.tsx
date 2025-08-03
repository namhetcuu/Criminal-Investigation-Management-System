"use client";

import { Button } from "@/components/ui/button";
import {
  Dialog,
  DialogContent,
  DialogDescription,
  DialogFooter,
  DialogHeader,
  DialogTitle,
} from "@/components/ui/dialog";
import { CheckCircle } from "lucide-react";

/**
 * SUBMIT SUCCESS MODAL COMPONENT
 * 
 * A confirmation modal for successful submission with positive messaging.
 * Features a horizontal rectangular layout with success styling.
 * 
 * USAGE:
 * <SubmitSuccessModal
 *   isOpen={showSuccessModal}
 *   onClose={() => setShowSuccessModal(false)}
 *   onContinue={() => handleContinue()}
 *   title="Field Report Submitted Successfully!" // Optional: custom title
 *   itemName="Field Report #R-2024-001" // Optional: specific item name
 * />
 * 
 * FEATURES:
 * - Horizontal rectangular layout
 * - Success messaging with positive styling
 * - Continue and Close action buttons
 * - Success icon for visual emphasis
 * - Positive styling for continue button
 */

interface SubmitSuccessModalProps {
  isOpen: boolean;                    // Controls modal visibility
  onClose: () => void;               // Handler to close modal
  onContinue?: () => void;           // Optional: Handler to continue to next action
  title?: string;                    // Optional: custom success title
  itemName?: string;                 // Optional: specific item name to display
  message?: string;                  // Optional: custom success message
}

export function SubmitSuccessModal({ 
  isOpen, 
  onClose, 
  onContinue,
  title = "Submission Successful!",
  itemName,
  message
}: SubmitSuccessModalProps) {
  
  const handleContinue = () => {
    if (onContinue) {
      onContinue();
    }
    onClose();
  };

  const defaultMessage = "Your submission has been successfully saved to the system. All data has been recorded and is now available for review by authorized personnel.";

  return (
    <Dialog open={isOpen} onOpenChange={onClose}>
      <DialogContent className="max-w-2xl w-full">
        {/* MODAL HEADER */}
        <DialogHeader className="text-center space-y-4">
          {/* SUCCESS ICON */}
          <div className="flex justify-center">
            <div className="w-16 h-16 bg-green-100 rounded-full flex items-center justify-center">
              <CheckCircle className="w-8 h-8 text-green-600" />
            </div>
          </div>
          
          {/* MAIN TITLE */}
          <DialogTitle className="text-2xl font-bold text-gray-900">
            {title}
            {itemName && (
              <div className="text-lg font-medium text-green-600 mt-2">
                {itemName}
              </div>
            )}
          </DialogTitle>
        </DialogHeader>

        {/* SUCCESS MESSAGE */}
        <DialogDescription className="text-center space-y-4 py-6">
          <div className="text-base text-gray-700 leading-relaxed">
            <span className="font-bold text-green-600">Success:</span>{" "}
            {message || defaultMessage}
          </div>
          
          <div className="text-base text-gray-700">
            The information has been{" "}
            <span className="font-bold">securely stored</span>{" "}
            and is now part of the official case record.
          </div>
        </DialogDescription>

        {/* ACTION BUTTONS */}
        <DialogFooter className="flex justify-center gap-4 pt-6">
          {/* CLOSE BUTTON */}
          <Button
            variant="outline"
            onClick={onClose}
            className="px-8 py-2 rounded-lg border-gray-300 hover:bg-gray-50"
          >
            Close
          </Button>
          
          {/* CONTINUE BUTTON (Only show if onContinue is provided) */}
          {onContinue && (
            <Button
              onClick={handleContinue}
              className="px-8 py-2 rounded-lg bg-green-600 hover:bg-green-700 text-white font-medium"
            >
              Continue
            </Button>
          )}
        </DialogFooter>
      </DialogContent>
    </Dialog>
  );
}