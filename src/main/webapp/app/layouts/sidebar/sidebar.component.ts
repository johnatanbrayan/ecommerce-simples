import { Component, OnInit } from '@angular/core';
import { AccountService } from 'app/core/auth/account.service';
import { trigger, state, style, animate, transition } from '@angular/animations';


@Component({
  selector: 'jhi-sidebar',
  templateUrl: './sidebar.component.html',
  styleUrls: ['sidebar.scss'],
  animations: [
    trigger('smoothCollapse', [
      state(
        'initial',
        style({
          height: '0',
          overflow: 'hidden',
          opacity: 0.5,
        })
      ),
      state(
        'final',
        style({
          overflow: 'hidden',
          opacity: 1,
        })
      ),
      transition('initial=>final', animate('400ms')),
      transition('final=>initial', animate('400ms')),
    ]),
  ],
})
export class SidebarComponent implements OnInit {
  isCollapsed1 = false;
  isCollapsed2 = false;
  isCollapsed3 = false;
  isCollapsed4 = false;

  constructor(private accountService: AccountService) {}

  ngOnInit(): void {
    this.isCollapsed1 = true;
    this.isCollapsed2 = true;
    this.isCollapsed3 = true;
    this.isCollapsed4 = true;
  }

  isAuthenticated(): boolean {
    return this.accountService.isAuthenticated();
  }
}
