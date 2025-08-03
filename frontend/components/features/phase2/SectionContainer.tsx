/**
 * SECTION CONTAINER COMPONENT
 * 
 * A reusable wrapper component that provides consistent styling and layout
 * for form sections. Includes a label, optional ADD button, and children content.
 * 
 * USAGE:
 * <SectionContainer
 *   label="LIST OF OFFICERS"
 *   onAdd={handleAddOfficer}        // Optional: shows ADD button if provided
 *   addButtonText="ADD OFFICER"     // Optional: custom button text (defaults to "ADD")
 *   className="mb-8"                // Optional: custom CSS classes (defaults to "mb-6")
 * >
 *   <DataTable columns={columns} data={data} />
 * </SectionContainer>
 * 
 * FEATURES:
 * - Consistent white background with padding
 * - Header with label and optional ADD button
 * - ADD button includes plus icon in circle design
 * - Flexible content area for any child components
 * - Customizable button text and container classes
 */

import { Plus } from "lucide-react";
import { Button } from "@/components/ui/button";

// Component props interface
interface SectionContainerProps {
  label: string;                   // The section title/label
  onAdd?: () => void;              // Optional add button click handler
  addButtonText?: string;          // Optional custom button text (defaults to "ADD")
  icon?: boolean;                  // Optional icon to display in the button
  children: React.ReactNode;       // Content to render inside the section
  className?: string;              // Optional custom CSS classes (defaults to "mb-6")
}

export function SectionContainer({ 
  label, 
  onAdd, 
  addButtonText = "ADD",           // Default button text
  icon = true,                            // Default: show icon unless set to true
  children, 
  className = "mb-6"               // Default margin bottom
}: SectionContainerProps) {
  return (
    // Main container with white background and padding
    <div className={`${className} bg-white p-4 rounded-lg`}>
      
      {/* SECTION HEADER */}
      {/* Contains the label and optional ADD button */}
      <div className="flex justify-between items-center mb-2">
        
        {/* Section label */}
        <label className="font-semibold text-sm">{label}</label>
        
        {/* ADD BUTTON */}
        {/* Only renders if onAdd handler is provided */}
        {onAdd && (
          <Button 
            size="sm" 
            variant="outline" 
            className="rounded-md flex items-center gap-2 bg-blue-100"
            onClick={onAdd}
          >
            {/* Button text */}
            {addButtonText}
            
            {/* Plus icon in circular border */}
            {/* Uses border-current to match text color */}
            {icon && (
            <div className="w-4 h-4 rounded-full border border-current flex items-center justify-center">
              <Plus className="w-2 h-2" />
            </div>
            )}
          </Button>
        )}
      </div>
      
      {/* SECTION CONTENT */}
      {/* Renders whatever children are passed in */}
      {children}
    </div>
  );
}