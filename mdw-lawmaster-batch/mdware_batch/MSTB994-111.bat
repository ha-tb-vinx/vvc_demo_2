@echo off
rem =======================================
rem MSTB994-111 �w���POS�pIF��ʃ}�X�^�쐬
rem =======================================
call %~dp0\SetBatchPath.bat
%execjava% mdware.common.batch.util.control.BatchController MSTB994-111 %CLS_HOME%\batch\properties release use
exit %ERRORLEVEL%
