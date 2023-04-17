import { Overlay, OverlayConfig } from '@angular/cdk/overlay';
import { ComponentPortal } from '@angular/cdk/portal';
import { Component, Inject } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { DisclaimerComponent } from '../disclaimer/disclaimer.component';


@Component({
  selector: 'app-modal',
  templateUrl: './modal.component.html',
  styleUrls: ['./modal.component.css']
})
export class ModalComponent {
 
}
