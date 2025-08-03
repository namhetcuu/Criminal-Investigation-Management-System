/**
 * ACTION BUTTONS COMPONENT
 * 
 * A reusable component that renders action buttons for table rows.
 * Supports three types of actions: Edit, Delete, and View.
 * 
 * USAGE:
 * <ActionButtons
 *   row={rowData}
 *   index={rowIndex}
 *   onEdit={handleEdit}     // Optional: shows edit button if provided
 *   onDelete={handleDelete} // Optional: shows delete button if provided
 *   onView={handleView}     // Optional: shows view button if provided
 * />
 * 
 * FEATURES:
 * - Conditionally renders buttons based on which handlers are provided
 * - Uses Lucide React icons for consistent styling
 * - Rounded button design with hover effects
 * - Passes row data and index to handlers for context
 */

import { Trash2, Edit, ChevronRight } from "lucide-react";
import { Button } from "@/components/ui/button";

interface ActionButtonsProps {
  onEdit?: (row: any, index: number) => void;    // Optional edit handler
  onDelete?: (row: any, index: number) => void;  // Optional delete handler  
  onView?: (row: any, index: number) => void;    // Optional view handler
  row: any;      // The data object for this row
  index: number; // The row index in the table
}

export function ActionButtons({ onEdit, onDelete, onView, row, index }: ActionButtonsProps) {
  return (
    <>
      {/* DELETE BUTTON */}
      {/* Only renders if onDelete handler is provided */}
      {onDelete && (
        <Button 
          size="sm" 
          variant="ghost" 
          className="rounded-full"
          onClick={() => onDelete(row, index)}
        >
          <Trash2 className="w-4 h-4 text-black" />
        </Button>
      )}
      
      {/* EDIT BUTTON */}
      {/* Only renders if onEdit handler is provided */}
      {onEdit && (
        <Button 
          size="sm" 
          variant="ghost" 
          className="rounded-full"
          onClick={() => onEdit(row, index)}
        >
          <Edit className="w-4 h-4 text-black" />
        </Button>
      )}
      
      {/* VIEW BUTTON */}
      {/* Only renders if onView handler is provided */}
      {/* Uses a custom circular border with chevron icon inside */}
      {onView && (
        <Button 
          size="sm" 
          variant="ghost" 
          className="rounded-full"
          onClick={() => onView(row, index)}
        >
          <div className="w-4 h-4 rounded-full border border-black flex items-center justify-center">
            <ChevronRight className="w-2 h-2 text-black" />
          </div>
        </Button>
      )}
    </>
  );
}